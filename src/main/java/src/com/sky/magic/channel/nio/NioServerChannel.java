package com.sky.magic.channel.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import com.sky.magic.channel.AbstractChannel;
import com.sky.magic.channel.ChannelException;
import com.sky.magic.channel.EventLoop;
import com.sky.magic.channel.nio.eventloop.NioEventLoop;
import com.sky.magic.channel.nio.eventloop.boss.NioServerBoss;
import com.sky.magic.util.MLog;

public class NioServerChannel extends AbstractChannel {
	private static final String TAG = NioServerChannel.class.getSimpleName();
	
	private ServerSocketChannel socket = null;
	private NioEventLoop bossLoop = null;
	
	public NioServerChannel() {
		// 空构造器
	}
	
	public void bind(InetSocketAddress address) {
		bindNow(address);
		registerKeys();
		loopEvent();
	}

	private void bindNow(InetSocketAddress address) {
		try {
			getSocket().bind(address);
		} catch (IOException e) {
			throw new ChannelException("Channel bind exception.", e);
		}
	}
	
	private void registerKeys() {
		MLog.log(TAG, "3.Register accept key on selector.", "server-start");
		try {
			getSocket().register(getEventLoop().getSelector(), SelectionKey.OP_ACCEPT, this);
		} catch (ClosedChannelException e) {
			throw new ChannelException("Channel has been closed, so can not register key.", e);
		}
	}
	
	public void loopEvent() {
		// 异步启动事件循环
		new Thread(getEventLoop()).start();
	}
	
	public SocketChannel acceptSocket() {
		SocketChannel socket = null;
		try {
			socket = getSocket().accept();
			// 设置为非阻塞
			socket.configureBlocking(false);
		} catch (IOException e) {
			throw new ChannelException("Failed to accept a channel.", e);
		}
		return socket;
	}

	public ServerSocketChannel getSocket() {
		if(socket==null) {
			try {
				socket = ServerSocketChannel.open();
				socket.configureBlocking(false);
			} catch (IOException e) {
				throw new ChannelException("Failed to open a server channel.", e);
			}
		}
		return socket;
	}
	
	private EventLoop getEventLoop() {
		if(bossLoop==null) {
			bossLoop = new NioServerBoss();
		}
		return bossLoop;
	}
}
