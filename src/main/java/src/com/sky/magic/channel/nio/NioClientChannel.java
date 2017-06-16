package com.sky.magic.channel.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;

import com.sky.magic.channel.ChannelException;
import com.sky.magic.channel.nio.eventloop.EventLoop;
import com.sky.magic.channel.nio.eventloop.boss.NioClientBoss;
import com.sky.magic.util.Constants;
import com.sky.magic.util.MLog;

public class NioClientChannel extends NioSocketChannel {
	private static final String TAG = NioClientChannel.class.getSimpleName();

	public NioClientChannel() {
		// ¿Õ¹¹ÔìÆ÷
	}

	public void connect(InetSocketAddress address) {
		connectNow(address);
		registerKeys();
		loopEvent();
	}

	private void connectNow(InetSocketAddress address) {
		try {
			getSocket().connect(address);
		} catch (IOException e) {
			throw new ChannelException("Channel connect exception.", e);
		}
	}

	private void registerKeys() {
		MLog.log(TAG, "3.Register connect key on selector.", Constants.LOG_CLIENT_START);
		try {
			getSocket().register(getEventLoop().getSelector(), SelectionKey.OP_CONNECT, this);
		} catch (ClosedChannelException e) {
			throw new ChannelException(
					"Channel has been closed, so can not register key.");
		}
	}

	public EventLoop createEventLoop() {
		return new NioClientBoss();
	}
}
