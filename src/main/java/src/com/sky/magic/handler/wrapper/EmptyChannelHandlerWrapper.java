package com.sky.magic.handler.wrapper;

import com.sky.magic.handler.ChannelHandler;
import com.sky.magic.handler.EmptyChannelHandler;

public class EmptyChannelHandlerWrapper implements ChannelHandlerWrapper {
	public ChannelHandlerWrapper getPrevWrapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPrevWrapper(ChannelHandlerWrapper wrapper) {
		// TODO Auto-generated method stub
	}

	public ChannelHandlerWrapper getNextWrapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setNextWrapper(ChannelHandlerWrapper wrapper) {
		// TODO Auto-generated method stub
	}
	
	public void addBefore(ChannelHandlerWrapper wrapper) {
		// TODO Auto-generated method stub
	}

	public void addAfter(ChannelHandlerWrapper wrapper) {
		// TODO Auto-generated method stub
	}
	
	public String getName() {
		return "Empty";
	}

	public ChannelHandler getHandler() {
		return new EmptyChannelHandler();
	}
	
	public String toString() {
		return "Empty";
	}
}
