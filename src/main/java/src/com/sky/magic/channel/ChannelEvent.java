package com.sky.magic.channel;

public class ChannelEvent {
	private Channel channel = null;
	
	public ChannelEvent(Channel channel) {
		this.channel = channel;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}
