package com.sky.magic.channel;

public interface ChannelHandler {
	void handleEvent(ChannelEvent event);
	
	ChannelHandler getPrevHandler();
	
	void setPrevHandler(ChannelHandler handler);
	
	ChannelHandler getNextHandler();
	
	void setNextHandler(ChannelHandler handler);
}
