package com.sky.magic.handler;

import com.sky.magic.channel.ChannelEvent;

public class EmptyChannelHandler implements ChannelHandler {
	public boolean handleEvent(ChannelEvent event) {
		return false;
	}
}
