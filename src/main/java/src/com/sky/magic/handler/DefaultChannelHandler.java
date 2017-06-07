package com.sky.magic.handler;

import com.sky.magic.channel.ChannelEvent;

/**
 * 数据处理器默认实现
 * @author xufan
 */
public class DefaultChannelHandler implements ChannelHandler {
	private ChannelHandler prevHandler = null;
	private ChannelHandler nextHandler = null;
	
	public void handleEvent(ChannelEvent event) {
		// TODO Auto-generated method stub
	}

	public ChannelHandler getPrevHandler() {
		if(prevHandler==null) {
			prevHandler = new EmptyChannelHandler();
		}
		return prevHandler;
	}

	public void setPrevHandler(ChannelHandler prevHandler) {
		this.prevHandler = prevHandler;
	}

	public ChannelHandler getNextHandler() {
		if(nextHandler==null) {
			nextHandler = new EmptyChannelHandler();
		}
		return nextHandler;
	}

	public void setNextHandler(ChannelHandler nextHandler) {
		this.nextHandler = nextHandler;
	}
}
