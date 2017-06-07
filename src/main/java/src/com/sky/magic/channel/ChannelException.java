package com.sky.magic.channel;

public class ChannelException extends RuntimeException {
	public ChannelException() {
	}
	
	public ChannelException(String message) {
		super(message);
	}
	
	public ChannelException(Throwable cause) {
		super(cause);
	}
	
	public ChannelException(String message, Throwable cause) {
		super(message, cause);
	}
}
