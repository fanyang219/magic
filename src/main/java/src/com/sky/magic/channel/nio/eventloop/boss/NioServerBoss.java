package com.sky.magic.channel.nio.eventloop.boss;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.sky.magic.channel.nio.NioServerChannel;
import com.sky.magic.channel.nio.NioWorkerChannel;
import com.sky.magic.channel.nio.eventloop.NioEventLoop;
import com.sky.magic.handler.chain.ChannelChain;
import com.sky.magic.util.MLog;

/**
 * ������¼��ַ���
 * @author xufan
 */
public class NioServerBoss extends NioEventLoop {
	private static final String TAG = NioServerBoss.class.getSimpleName();
	
	public NioServerBoss() {
		super("server-boss");
	}

	public void processEvent(SelectionKey key) {
		MLog.log(TAG, "Start to process single event.", getName());
		if(key.isAcceptable()) { // ����˽��յ��ͻ�������
			// 1.��ȡnioͨ��
			NioServerChannel serverChannel = (NioServerChannel) key.attachment();
			// 2.��ȡ����ͨ��
			SocketChannel workerSocket = serverChannel.acceptSocket();
			// 3.����NioAcceptChannel
			NioWorkerChannel workerChannel = new NioWorkerChannel(workerSocket);
			// 4.�󶨴�����(ServerBootstrap���õ�ChannelChainFactory)
			ChannelChain workerChain = serverChannel.getConfig().getChainFactory().getChannelChain();
			workerChannel.setChain(workerChain);
			// 5.��ʼ����(��д����)
			workerChannel.work();
		}
	}
}
