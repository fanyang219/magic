package com.sky.magic.handler.chain;

import com.sky.magic.channel.ChannelEvent;
import com.sky.magic.handler.ChannelHandler;
import com.sky.magic.handler.wrapper.ChannelHandlerWrapper;

public interface ChannelChain {
	void addFirst(String name, ChannelHandler handler);
	
	void addLast(String name, ChannelHandler handler);
	
	void addBefore(String baseName, String name, ChannelHandler handler);
	
	void addAfter(String baseName, String name, ChannelHandler handler);
	
	void clear();
	
	ChannelHandler getChannelHandler(String name);
	
	ChannelHandlerWrapper getChannelHandlerWrapper(String name);
	
	// 处理上游事件
	void handleUpstream(ChannelEvent event);
	
	// 处理下游事件
	void handleDownstream(ChannelEvent event);
}
