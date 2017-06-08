package com.sky.magic.handler.wrapper;

import com.sky.magic.handler.ChannelHandler;

public interface ChannelHandlerWrapper {
	ChannelHandlerWrapper getPrevWrapper();
	
	void setPrevWrapper(ChannelHandlerWrapper wrapper);
	
	ChannelHandlerWrapper getNextWrapper();
	
	void setNextWrapper(ChannelHandlerWrapper wrapper);
	
	void addBefore(ChannelHandlerWrapper wrapper);
	
	void addAfter(ChannelHandlerWrapper wrapper);
	
	ChannelHandler getHandler();
	
	String getName();
}
