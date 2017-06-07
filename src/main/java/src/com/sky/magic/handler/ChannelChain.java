package com.sky.magic.handler;

import com.sky.magic.channel.ChannelEvent;

public interface ChannelChain {
	void addFirst(String name, ChannelHandler handler);
	
	void addLast(String name, ChannelHandler handler);
	
	void addBefore(String baseName, String name, ChannelHandler handler);
	
	void addAfter(String baseName, String name, ChannelHandler handler);
	
	void clear();
	
	ChannelHandler getChannelHandler(String name);
	
	// 处理通道事件
	void handleEvent(ChannelEvent event);
}
