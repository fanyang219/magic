package com.sky.magic.channel.nio.eventloop.boss;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.sky.magic.channel.nio.NioServerChannel;
import com.sky.magic.channel.nio.NioWorkerChannel;
import com.sky.magic.channel.nio.eventloop.NioEventLoop;
import com.sky.magic.handler.chain.ChannelChain;
import com.sky.magic.util.MLog;

/**
 * 服务端事件分发器
 * @author xufan
 */
public class NioServerBoss extends NioEventLoop {
	private static final String TAG = NioServerBoss.class.getSimpleName();
	
	public NioServerBoss() {
		super("server-boss");
	}

	public void processEvent(SelectionKey key) {
		MLog.log(TAG, "Start to process single event.", getName());
		if(key.isAcceptable()) { // 服务端接收到客户端连接
			// 1.获取nio通道
			NioServerChannel serverChannel = (NioServerChannel) key.attachment();
			// 2.获取数据通道
			SocketChannel workerSocket = serverChannel.acceptSocket();
			// 3.创建NioAcceptChannel
			NioWorkerChannel workerChannel = new NioWorkerChannel(workerSocket);
			// 4.绑定处理器(ServerBootstrap设置的ChannelChainFactory)
			ChannelChain workerChain = serverChannel.getConfig().getChainFactory().getChannelChain();
			workerChannel.setChain(workerChain);
			// 5.开始工作(读写数据)
			workerChannel.work();
		}
	}
}
