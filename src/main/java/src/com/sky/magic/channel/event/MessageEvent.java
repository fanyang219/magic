package com.sky.magic.channel.event;

import com.sky.magic.channel.ChannelEvent;

public class MessageEvent extends ChannelEvent {
	private String message = "";

	public MessageEvent(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
