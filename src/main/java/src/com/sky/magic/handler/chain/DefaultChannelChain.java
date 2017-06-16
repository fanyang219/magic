package com.sky.magic.handler.chain;

import java.util.HashMap;
import java.util.Map;

import com.sky.magic.channel.ChannelEvent;
import com.sky.magic.handler.ChannelHandler;
import com.sky.magic.handler.sink.ChannelSink;
import com.sky.magic.handler.sink.EmptyChannelSink;
import com.sky.magic.handler.wrapper.ChannelHandlerWrapper;
import com.sky.magic.handler.wrapper.DefaultChannelHandlerWrapper;
import com.sky.magic.util.MLog;

/**
 * ���ݴ��������(������ģʽ)
 * ְ�𣺹��������ݴ��������Զ��崦���������༰˳��
 * ���Է������չ���ݴ���ҵ��
 * @author xufan
 */
public class DefaultChannelChain implements ChannelChain {
	private static final String TAG = DefaultChannelChain.class.getSimpleName();
	
	private Map<String, ChannelHandlerWrapper> wrapperMap = null;
	private ChannelHandlerWrapper headWrapper = null;
	private ChannelHandlerWrapper tailWrapper = null;
	
	private ChannelSink channelSink = null;
	
	public void addFirst(String name, ChannelHandler handler) {
		// У���Ƿ��Ѵ���
		checkDuplicateName(name);
		ChannelHandlerWrapper wrapper = handlerToWrapper(name, handler);
		
		if(headWrapper==null || tailWrapper==null) { // ��������
			headWrapper = wrapper;
			tailWrapper = wrapper;
			getWrapperMap().put(name, wrapper);
			return;
		}
		
		// ����headWrapperǰ��
		wrapper.addBefore(headWrapper);
		// ����headWrapper
		headWrapper = wrapper;
		
		getWrapperMap().put(name, wrapper);
	}
	
	public void addLast(String name, ChannelHandler handler) {
		// У���Ƿ��Ѵ���
		checkDuplicateName(name);
		ChannelHandlerWrapper wrapper = handlerToWrapper(name, handler);
		
		if(headWrapper==null || tailWrapper==null) { // ��������
			headWrapper = wrapper;
			tailWrapper = wrapper;
			getWrapperMap().put(name, wrapper);
			return;
		}
		
		// ����tailWrapper����
		wrapper.addAfter(tailWrapper);
		// ����tailWrapper
		tailWrapper = wrapper;
		
		getWrapperMap().put(name, wrapper);
	}

	public void addBefore(String baseName, String name, ChannelHandler handler) {
		// У���Ƿ��Ѵ���
		checkDuplicateName(name);
		if(headWrapper==null || tailWrapper==null) { // ��������
			addFirst(name, handler);
			return;
		}
		
		ChannelHandlerWrapper wrapper = handlerToWrapper(name, handler);
		ChannelHandlerWrapper baseWrapper = getWrapperMap().get(baseName);
		if(baseWrapper==null) { // �����ڲ���handler
			return;
		}
		
		if(baseWrapper==headWrapper) { // ����handler��ͷ��
			addFirst(name, handler);
			return;
		}

		// ����baseWrapper.getPrevWrapper()����,����baseWrapperǰ��
		wrapper.addBetween(baseWrapper.getPrevWrapper(), baseWrapper);
		
		getWrapperMap().put(name, wrapper);
	}

	public void addAfter(String baseName, String name, ChannelHandler handler) {
		// У���Ƿ��Ѵ���
		checkDuplicateName(name);
		if(headWrapper==null || tailWrapper==null) { // ��������
			addLast(name, handler);
			return;
		}
		
		ChannelHandlerWrapper wrapper = handlerToWrapper(name, handler);
		ChannelHandlerWrapper baseWrapper = getWrapperMap().get(baseName);
		if(baseWrapper==null) { // �����ڲ���handler
			return;
		}
		
		if(baseWrapper==tailWrapper) { // ����handler��ͷ��
			addLast(name, handler);
			return;
		}
		
		// ����baseWrapper.getNextWrapper()ǰ��,����baseWrapper����
		wrapper.addBetween(baseWrapper, baseWrapper.getNextWrapper());
		
		getWrapperMap().put(name, wrapper);
	}
	
	public void clear() {
		getWrapperMap().clear();
		
		wrapperMap = null;
		headWrapper = null;
		tailWrapper = null;
	}
	
	private void checkDuplicateName(String name) {
		if(getWrapperMap().containsKey(name)) {
			throw new IllegalArgumentException("Duplicate handler name:"+name);
		}
	}
	
	public ChannelHandlerWrapper handlerToWrapper(String name, ChannelHandler handler) {
		return new DefaultChannelHandlerWrapper(name, handler);
	}

	public ChannelHandler getChannelHandler(String name) {
		return getChannelHandlerWrapper(name).getHandler();
	}
	
	public ChannelHandlerWrapper getChannelHandlerWrapper(String name) {
		return getWrapperMap().get(name);
	}
	
	private Map<String, ChannelHandlerWrapper> getWrapperMap() {
		if(wrapperMap==null) {
			wrapperMap = new HashMap<String, ChannelHandlerWrapper>();
		}
		return wrapperMap;
	}

	// ���´���ͨ���¼�
	public void handleDownstream(ChannelEvent event) {
		if(tailWrapper==null) {
			MLog.log(TAG, "The chain contains no handlers, discarding:"+event);
			return;
		}
		// ��ʽ�����¼�(��ǰ������ִ����Ϻ��Զ�������һ��������)
		handleDownstream(tailWrapper, event);
	}
	
	// ��ĳ���ڵ����£������¼�
	public void handleDownstream(ChannelHandlerWrapper wrapper, ChannelEvent event) {
		boolean handleFlag = wrapper.getHandler().handleEvent(event);
//		if(!handleFlag && wrapper.hasPrevWrapper()) { // ����¼�δ�������Ҵ���ǰ��Ľڵ㣬�򽻸�ǰ��Ľڵ㴦��
		// TODO �Ƿ���Ҫ����handleFlag
		if(wrapper.hasPrevWrapper()) { // �������ǰ��Ľڵ㣬�򽻸�ǰ��Ľڵ㴦��
			MLog.log(TAG, "The wrapper has prev wrapper, so call previous wrapper to handle:"+event);
			handleDownstream(wrapper.getPrevWrapper(), event);
		} else { // ���û��ǰ��Ľڵ㣬ҵ�������Ѿ�������ϣ��򽻸�channelSink��socketֱ�ӽ���
			MLog.log(TAG, "The wrapper has no prev wrapper, so call channel sink to handle.");
			getChannelSink().handleSink(event);
		}
	}
	
	// ���ϴ���ͨ���¼�
	public void handleUpstream(ChannelEvent event) {
		if(headWrapper==null) {
			MLog.log(TAG, "The chain contains no handlers, discarding:"+event);
			return;
		}
		// ��ʽ�����¼�(��ǰ������ִ����Ϻ��Զ�������һ��������)
		handleUpstream(headWrapper, event);
	}
	
	// ��ĳ���ڵ����ϣ������¼�
	public void handleUpstream(ChannelHandlerWrapper wrapper, ChannelEvent event) {
		wrapper.getHandler().handleEvent(event);
		if(wrapper.hasNextWrapper()) { // ����¼�δ�������Ҵ��ں���Ľڵ㣬�򽻸�����Ľڵ㴦��
			MLog.log(TAG, "The wrapper has next wrapper, so call next wrapper to handle:"+event);
			handleUpstream(wrapper.getNextWrapper(), event);
		} else {
			MLog.log(TAG, "The wrapper has no next wrapper, so stop handle.");
		}
	}
	
	// ��ȡ��channelֱ�ӽ����Ĵ�����(��д��)
	private ChannelSink getChannelSink() {
		if(channelSink == null) {
			channelSink = new EmptyChannelSink();
		}
		return channelSink;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getClass().getSimpleName());
		buffer.append("{");
		
		ChannelHandlerWrapper wrapper = headWrapper;
		if(wrapper!=null) {
			for(;;) {
				buffer.append("(");
				buffer.append(wrapper.getName());
				buffer.append(" = ");
				buffer.append(wrapper.getHandler().getClass().getSimpleName());
				buffer.append(")");
				wrapper = wrapper.getNextWrapper();
				if(wrapper==null) {
					break;
				}
				buffer.append(", ");
			}
		}
		
		buffer.append("}");
		return buffer.toString();
	}
}
