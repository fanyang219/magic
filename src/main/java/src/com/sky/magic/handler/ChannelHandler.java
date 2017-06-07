package com.sky.magic.handler;

import com.sky.magic.channel.ChannelEvent;

public interface ChannelHandler {
	void handleEvent(ChannelEvent event);
	
	ChannelHandler getPrevHandler();
	
	void setPrevHandler(ChannelHandler handler);
	
	ChannelHandler getNextHandler();
	
	void setNextHandler(ChannelHandler handler);
}
