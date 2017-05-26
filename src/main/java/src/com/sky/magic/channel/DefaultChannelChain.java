package com.sky.magic.channel;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据处理管理链(责任链模式)
 * 职责：管理多个数据处理器，自定义处理器的种类及顺序
 * 可以方便的扩展数据处理业务
 * @author xufan
 */
public class DefaultChannelChain implements ChannelChain {
	private Map<String, ChannelHandler> handlerMap = null;
	private ChannelHandler headHandler = null;
	private ChannelHandler tailHandler = null;
	
	public void addFirst(String name, ChannelHandler handler) {
		if(headHandler==null || tailHandler==null) { // 保护处理
			headHandler = handler;
			tailHandler = handler;
			getHandlerMap().put(name, handler);
			return;
		}
		
		handler.setNextHandler(headHandler);
		headHandler.setPrevHandler(handler);
		
		getHandlerMap().put(name, handler);
	}
	
	public void addLast(String name, ChannelHandler handler) {
		if(headHandler==null || tailHandler==null) { // 保护处理
			headHandler = handler;
			tailHandler = handler;
			getHandlerMap().put(name, handler);
			return;
		}
		
		tailHandler.setNextHandler(handler);
		handler.setPrevHandler(tailHandler);
		
		getHandlerMap().put(name, handler);
	}

	public void addBefore(String baseName, String name, ChannelHandler handler) {
		// TODO name已存在
		if(headHandler==null || tailHandler==null) { // 保护处理
			addFirst(name, handler);
			return;
		}
		
		ChannelHandler baseHandler = getHandlerMap().get(baseName);
		if(baseHandler==null) { // 不存在参照handler
			return;
		}
		
		if(baseHandler==headHandler) { // 参照handler在头部
			addFirst(name, handler);
			return;
		}
		
		// 将handler放在baseHandler前面
		// 与参照物前面的节点绑定关系
		baseHandler.getPrevHandler().setNextHandler(handler);
		handler.setPrevHandler(baseHandler.getPrevHandler());
		// 与参照物绑定关系
		handler.setNextHandler(baseHandler);
		baseHandler.setPrevHandler(handler);
		
		getHandlerMap().put(name, handler);
	}

	public void addAfter(String baseName, String name, ChannelHandler handler) {
		// TODO name已存在
		if(headHandler==null || tailHandler==null) { // 保护处理
			addLast(name, handler);
			return;
		}
		
		ChannelHandler baseHandler = getHandlerMap().get(baseName);
		if(baseHandler==null) { // 不存在参照handler
			return;
		}
		
		if(baseHandler==tailHandler) { // 参照handler在头部
			addLast(name, handler);
			return;
		}
		
		// 将handler放在baseHandler前面
		// 与参照物后面的节点绑定关系
		baseHandler.getNextHandler().setPrevHandler(handler);
		handler.setNextHandler(baseHandler.getNextHandler());
		// 与参照物绑定关系
		handler.setPrevHandler(baseHandler);
		baseHandler.setNextHandler(handler);
		
		getHandlerMap().put(name, handler);
	}
	
	public void clear() {
		getHandlerMap().clear();
		
		handlerMap = null;
		headHandler = null;
		tailHandler = null;
	}

	public ChannelHandler getChannelHandler(String name) {
		return getHandlerMap().get(name);
	}
	
	private Map<String, ChannelHandler> getHandlerMap() {
		if(handlerMap==null) {
			handlerMap = new HashMap<String, ChannelHandler>();
		}
		return handlerMap;
	}
}
