package com.sky.magic.handler;

import com.sky.magic.channel.ChannelEvent;

public class SimpleChannelHandler implements ChannelHandler {
	public boolean handleEvent(ChannelEvent event) {
		return false;
	}
}
