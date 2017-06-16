package com.sky.magic.channel.nio.eventloop.worker;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.sky.magic.channel.event.MessageEvent;
import com.sky.magic.channel.nio.NioSocketChannel;
import com.sky.magic.channel.nio.eventloop.NioEventLoop;
import com.sky.magic.util.MLog;

public class NioWorker extends NioEventLoop implements Worker {
	private static final String TAG = NioWorker.class.getSimpleName();
	
	public NioWorker() {
		super("worker-loop");
	}

	@Override
	public void processEvent(SelectionKey key) {
		MLog.log(TAG, "Start to process single event.", getName());
		if(key.isReadable()) {
			readData(key);
		}
	}
	
	// TODO ��ȡ���ݵ��߼���Ҫ�Ż�(Buffer���Ȳ��������ò���֡���������),�ر�channel
	private void readData(SelectionKey key) {
		SocketChannel socket = (SocketChannel) key.channel();
		NioSocketChannel channel = (NioSocketChannel) key.attachment();
		
		// ��ȡ����
		ByteBuffer buffer = ByteBuffer.allocate(100);
		try {
			socket.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String msg = new String(buffer.array()).trim();
		MLog.log(TAG, "Server Read some data:"+msg, getName());
		
		
		// ��ȡ���ݺ�������Ϣ�¼��������δ���
		MessageEvent messageEvent = new MessageEvent(channel, msg);
		// �������ݴ�����
		channel.getChain().handleUpstream(messageEvent);
	}
}
