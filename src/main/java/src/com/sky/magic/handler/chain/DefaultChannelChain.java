package com.sky.magic.handler.chain;

import java.util.HashMap;
import java.util.Map;

import com.sky.magic.channel.ChannelEvent;
import com.sky.magic.handler.ChannelHandler;
import com.sky.magic.handler.wrapper.ChannelHandlerWrapper;
import com.sky.magic.handler.wrapper.DefaultChannelHandlerWrapper;
import com.sky.magic.util.MLog;

/**
 * 数据处理管理链(责任链模式)
 * 职责：管理多个数据处理器，自定义处理器的种类及顺序
 * 可以方便的扩展数据处理业务
 * @author xufan
 */
public class DefaultChannelChain implements ChannelChain {
	private static final String TAG = DefaultChannelChain.class.getSimpleName();
	
	private Map<String, ChannelHandlerWrapper> wrapperMap = null;
	private ChannelHandlerWrapper headWrapper = null;
	private ChannelHandlerWrapper tailWrapper = null;
	
	public void addFirst(String name, ChannelHandler handler) {
		// 校验是否已存在
		checkDuplicateName(name);
		ChannelHandlerWrapper wrapper = handlerToWrapper(name, handler);
		
		if(headWrapper==null || tailWrapper==null) { // 保护处理
			headWrapper = wrapper;
			tailWrapper = wrapper;
			getWrapperMap().put(name, wrapper);
			return;
		}
		
		// 放在headWrapper前面
		wrapper.addBefore(headWrapper);
		// 更新headWrapper
		headWrapper = wrapper;
		
		getWrapperMap().put(name, wrapper);
	}
	
	public void addLast(String name, ChannelHandler handler) {
		// 校验是否已存在
		checkDuplicateName(name);
		ChannelHandlerWrapper wrapper = handlerToWrapper(name, handler);
		
		if(headWrapper==null || tailWrapper==null) { // 保护处理
			headWrapper = wrapper;
			tailWrapper = wrapper;
			getWrapperMap().put(name, wrapper);
			return;
		}
		
		// 放在tailWrapper后面
		wrapper.addAfter(tailWrapper);
		// 更新tailWrapper
		tailWrapper = wrapper;
		
		getWrapperMap().put(name, wrapper);
	}

	public void addBefore(String baseName, String name, ChannelHandler handler) {
		// 校验是否已存在
		checkDuplicateName(name);
		if(headWrapper==null || tailWrapper==null) { // 保护处理
			addFirst(name, handler);
			return;
		}
		
		ChannelHandlerWrapper wrapper = handlerToWrapper(name, handler);
		ChannelHandlerWrapper baseWrapper = getWrapperMap().get(baseName);
		if(baseWrapper==null) { // 不存在参照handler
			return;
		}
		
		if(baseWrapper==headWrapper) { // 参照handler在头部
			addFirst(name, handler);
			return;
		}

		// 放在baseWrapper.getPrevWrapper()后面,放在baseWrapper前面
		wrapper.addBetween(baseWrapper.getPrevWrapper(), baseWrapper);
		
		getWrapperMap().put(name, wrapper);
	}

	public void addAfter(String baseName, String name, ChannelHandler handler) {
		// 校验是否已存在
		checkDuplicateName(name);
		if(headWrapper==null || tailWrapper==null) { // 保护处理
			addLast(name, handler);
			return;
		}
		
		ChannelHandlerWrapper wrapper = handlerToWrapper(name, handler);
		ChannelHandlerWrapper baseWrapper = getWrapperMap().get(baseName);
		if(baseWrapper==null) { // 不存在参照handler
			return;
		}
		
		if(baseWrapper==tailWrapper) { // 参照handler在头部
			addLast(name, handler);
			return;
		}
		
		// 放在baseWrapper.getNextWrapper()前面,放在baseWrapper后面
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

	// 向后处理通道事件
	public void sendDownstream(ChannelEvent event) {
		if(tailWrapper==null) {
			MLog.log(TAG, "The chain contains no handlers, discarding:"+event);
			return;
		}
		// 链式处理事件(当前处理器执行完毕后，自动调用下一个处理器)
		sendDownstream(tailWrapper, event);
	}
	
	// 从某个节点往后，处理事件
	public void sendDownstream(ChannelHandlerWrapper wrapper, ChannelEvent event) {
		boolean isHandled = wrapper.getHandler().handleEvent(event);
		if(!isHandled && wrapper.hasPrevWrapper()) { // 如果事件未处理，并且存在前面的节点，则交给前面的节点处理
			MLog.log(TAG, "The wrapper has not handle, so call previous wrapper to handle:"+event);
			sendDownstream(wrapper.getPrevWrapper(), event);
		}
	}
	
	// 向后处理通道事件
	public void sendUpstream(ChannelEvent event) {
		if(headWrapper==null) {
			MLog.log(TAG, "The chain contains no handlers, discarding:"+event);
			return;
		}
		// 链式处理事件(当前处理器执行完毕后，自动调用下一个处理器)
		sendUpstream(headWrapper, event);
	}
	
	// 从某个节点往后，处理事件
	public void sendUpstream(ChannelHandlerWrapper wrapper, ChannelEvent event) {
		boolean isHandled = wrapper.getHandler().handleEvent(event);
		if(!isHandled && wrapper.hasNextWrapper()) { // 如果事件未处理，并且存在后面的节点，则交给后面的节点处理
			MLog.log(TAG, "The wrapper has not handle, so call next wrapper to handle:"+event);
			sendUpstream(wrapper.getNextWrapper(), event);
		} else {
			MLog.log(TAG, "The wrapper has handle, so stop handle.");
		}
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
