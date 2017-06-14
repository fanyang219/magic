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
	
	public boolean hasPrevWrapper() {
		return prevWrapper != null;
	}
	
	public ChannelHandlerWrapper getPrevWrapper() {
		return prevWrapper;
	}

	public void setPrevWrapper(ChannelHandlerWrapper prevWrapper) {
		this.prevWrapper = prevWrapper;
	}
	
	public boolean hasNextWrapper() {
		return nextWrapper != null;
	}

	public ChannelHandlerWrapper getNextWrapper() {
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

	public void addBetween(ChannelHandlerWrapper prevWrapper,
			ChannelHandlerWrapper nextWrapper) {
		addBefore(nextWrapper);
		addAfter(prevWrapper);
	}
	
	public String getName() {
		return name;
	}
	
	public ChannelHandler getHandler() {
		return handler;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("(");
		buffer.append("name:");
		buffer.append(name);
		buffer.append(", ");
		if(handler!=null) {
			buffer.append("handler:");
			buffer.append(handler.getClass().getSimpleName());
		}
		buffer.append(")");
		
		return buffer.toString();
	}
}
