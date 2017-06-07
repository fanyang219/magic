package com.sky.magic.channel.nio;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import com.sky.magic.channel.ChannelException;
import com.sky.magic.channel.EventLoop;
import com.sky.magic.channel.nio.eventloop.EmptyNioEventLoop;
import com.sky.magic.handler.ChannelChain;

public class NioSocketChannel extends AbstractNioChannel {
	private SocketChannel socket = null;
	
	public NioSocketChannel() {
		this(null);
	}
	
	public NioSocketChannel(SocketChannel socket) {
		this.socket = socket;
	}
	
	public SocketChannel getSocket() {
		if (socket == null) {
			try {
				socket = SocketChannel.open();
				socket.configureBlocking(false);
			} catch (IOException e) {
				throw new ChannelException("Failed to open a client channel.",
						e);
			}
		}
		return socket;
	}

	@Override
	public EventLoop createEventLoop() {
		return new EmptyNioEventLoop();
	}
}
