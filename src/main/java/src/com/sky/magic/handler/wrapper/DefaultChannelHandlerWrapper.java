package com.sky.magic.handler.wrapper;

import com.sky.magic.handler.ChannelHandler;

public class DefaultChannelHandlerWrapper implements ChannelHandlerWrapper {
	private ChannelHandlerWrapper prevWrapper = null;
	private ChannelHandlerWrapper nextWrapper = null;
	
	private String name = null;
	private ChannelHandler handler = null;
	
	public DefaultChannelHandlerWrapper(String name, ChannelHandler handler) {
		this.name = name;
		this.handler = handler;
	}
	
	public ChannelHandlerWrapper getPrevWrapper() {
		if(prevWrapper==null) {
			prevWrapper = new EmptyChannelHandlerWrapper();
		}
		return prevWrapper;
	}

	public void setPrevWrapper(ChannelHandlerWrapper prevWrapper) {
		this.prevWrapper = prevWrapper;
	}

	public ChannelHandlerWrapper getNextWrapper() {
		if(nextWrapper==null) {
			nextWrapper = new EmptyChannelHandlerWrapper();
		}
		return nextWrapper;
	}

	public void setNextWrapper(ChannelHandlerWrapper nextWrapper) {
		this.nextWrapper = nextWrapper;
	}
	
	public void addBefore(ChannelHandlerWrapper wrapper) {
		wrapper.setPrevWrapper(this);
		setNextWrapper(wrapper);
	}

	public void addAfter(ChannelHandlerWrapper wrapper) {
		wrapper.setNextWrapper(this);
		setPrevWrapper(wrapper);
	}

	public String getName() {
		return name;
	}
	
	public ChannelHandler getHandler() {
		return handler;
	}
	
	public String toString() {
		return "name:"+name
				+",prev:"+getPrevWrapper()
				+",next:"+getNextWrapper();
	}
}
