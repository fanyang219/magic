package com.sky.magic.channel.nio;

import com.sky.magic.channel.Channel;
import com.sky.magic.channel.ChannelFactory;

public class NioClientChannelFactory implements ChannelFactory {
	public Channel createChannel() {
		return new NioClientChannel();
	}
}
