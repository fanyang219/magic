package com.sky.magic.handler.wrapper;

import com.sky.magic.handler.ChannelHandler;

public interface ChannelHandlerWrapper {
	boolean hasPrevWrapper();
	
	ChannelHandlerWrapper getPrevWrapper();
	
	void setPrevWrapper(ChannelHandlerWrapper wrapper);
	
	boolean hasNextWrapper();
	
	ChannelHandlerWrapper getNextWrapper();
	
	void setNextWrapper(ChannelHandlerWrapper wrapper);
	
	void addBefore(ChannelHandlerWrapper wrapper);
	
	void addAfter(ChannelHandlerWrapper wrapper);
	
	void addBetween(ChannelHandlerWrapper prevWrapper, ChannelHandlerWrapper nextWrapper);
	
	ChannelHandler getHandler();
	
	String getName();
}
