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
		ChannelHandlerWrapper wrapper = handlerToWrapper(name, handler);
		
		if(headWrapper==null || tailWrapper==null) { // 保护处理
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
		
		if(headWrapper==null || tailWrapper==null) { // 保护处理
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
		// TODO name已存在
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

		// 与参照物前面的节点绑定关系,放在baseWrapper.getPrevWrapper()后面
		wrapper.addAfter(baseWrapper.getPrevWrapper());
		// 与参照物绑定关系,将wrapper放在baseWrapper前面
		wrapper.addBefore(baseWrapper);
		
		getWrapperMap().put(name, wrapper);
	}

	public void addAfter(String baseName, String name, ChannelHandler handler) {
		// TODO name已存在
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
		
		// 与参照物后面的节点绑定关系,放在baseWrapper.getNextWrapper()前面
		wrapper.addBefore(baseWrapper.getNextWrapper());
		// 与参照物绑定关系,放在baseWrapper后面
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

	// 处理通道事件
	public void handleEvent(ChannelEvent event) {
		if(headWrapper==null || tailWrapper==null) {
			MLog.log(TAG, "The chain contains no handlers, discarding:"+event);
			return;
		}
		// 链式处理事件(当前处理器执行完毕后，自动调用下一个处理器)
		headWrapper.getHandler().handleEvent(event);
	}
}
