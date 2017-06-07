package com.sky.magic.channel.nio;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.sky.magic.channel.ChannelException;
import com.sky.magic.channel.EventLoop;
import com.sky.magic.channel.nio.eventloop.worker.NioWorker;
import com.sky.magic.util.MLog;

public class NioWorkerChannel extends NioSocketChannel {
	private static final String TAG = NioWorkerChannel.class.getSimpleName();
	
	public NioWorkerChannel(SocketChannel socket) {
		super(socket);
	}
	
	public void work() {
		registerKeys();
		// 可触发绑定、连接状态
		loopEvent();
	}
	
	private void registerKeys() {
		MLog.log(TAG, "Register accept key on selector.", "io-handle");
		try {
			getSocket().register(getEventLoop().getSelector(), SelectionKey.OP_READ, this);
		} catch (ClosedChannelException e) {
			throw new ChannelException("Channel has been closed, so can not register key.", e);
		}
	}

	@Override
	public EventLoop createEventLoop() {
		return new NioWorker();
	}
}
