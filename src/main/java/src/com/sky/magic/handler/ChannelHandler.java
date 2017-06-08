package com.sky.magic.handler;

import com.sky.magic.channel.ChannelEvent;

public interface ChannelHandler {
	void handleEvent(ChannelEvent event);
}
