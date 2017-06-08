package com.sky.magic.handler.chain;

import java.util.HashMap;
import java.util.Map;

import com.sky.magic.channel.ChannelEvent;
import com.sky.magic.handler.ChannelHandler;
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
	
	public void addFirst(String name, ChannelHandler handler) {
		ChannelHandlerWrapper wrapper = handlerToWrapper(name, handler);
		
		if(headWrapper==null || tailWrapper==null) { // ��������
			headWrapper = wrapper;
			tailWrapper = wrapper;
			getWrapperMap().put(name, wrapper);
			return;
		}
		
		wrapper.addBefore(headWrapper);
		
		getWrapperMap().put(name, wrapper);
	}
	
	public void addLast(String name, ChannelHandler handler) {
		ChannelHandlerWrapper wrapper = handlerToWrapper(name, handler);
		
		if(headWrapper==null || tailWrapper==null) { // ��������
			headWrapper = wrapper;
			tailWrapper = wrapper;
			getWrapperMap().put(name, wrapper);
			return;
		}
		
		
		tailWrapper.setNextWrapper(wrapper);
		wrapper.setPrevWrapper(tailWrapper);
		
		getWrapperMap().put(name, wrapper);
	}

	public void addBefore(String baseName, String name, ChannelHandler handler) {
		// TODO name�Ѵ���
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

		// �������ǰ��Ľڵ�󶨹�ϵ,����baseWrapper.getPrevWrapper()����
		wrapper.addAfter(baseWrapper.getPrevWrapper());
		// �������󶨹�ϵ,��wrapper����baseWrapperǰ��
		wrapper.addBefore(baseWrapper);
		
		getWrapperMap().put(name, wrapper);
	}

	public void addAfter(String baseName, String name, ChannelHandler handler) {
		// TODO name�Ѵ���
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
		
		// ����������Ľڵ�󶨹�ϵ,����baseWrapper.getNextWrapper()ǰ��
		wrapper.addBefore(baseWrapper.getNextWrapper());
		// �������󶨹�ϵ,����baseWrapper����
		wrapper.addAfter(baseWrapper);
		
		getWrapperMap().put(name, wrapper);
	}
	
	public void clear() {
		getWrapperMap().clear();
		
		wrapperMap = null;
		headWrapper = null;
		tailWrapper = null;
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

	// ����ͨ���¼�
	public void handleEvent(ChannelEvent event) {
		if(headWrapper==null || tailWrapper==null) {
			MLog.log(TAG, "The chain contains no handlers, discarding:"+event);
			return;
		}
		// ��ʽ�����¼�(��ǰ������ִ����Ϻ��Զ�������һ��������)
		headWrapper.getHandler().handleEvent(event);
	}
}
