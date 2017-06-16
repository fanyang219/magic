package com.sky.magic.channel.event;

import com.sky.magic.channel.Channel;
import com.sky.magic.channel.ChannelEvent;

public class ChannelStateEvent extends ChannelEvent {
	public ChannelStateEvent(Channel channel) {
		super(channel);
	}
}
