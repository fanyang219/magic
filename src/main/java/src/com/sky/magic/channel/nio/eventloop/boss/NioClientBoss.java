package com.sky.magic.channel.nio.eventloop.boss;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.sky.magic.channel.nio.NioClientChannel;
import com.sky.magic.channel.nio.NioWorkerChannel;
import com.sky.magic.channel.nio.eventloop.NioEventLoop;
import com.sky.magic.util.MLog;

/**
 * �ͻ����¼��ַ���
 * @author xufan
 */
public class NioClientBoss extends NioEventLoop implements Boss {
	private static final String TAG = NioClientBoss.class.getSimpleName();
	
	public NioClientBoss() {
		super("client-boss");
	}

	public void processEvent(SelectionKey key) {
		MLog.log(TAG, "Start to process single event.", getName());
		if(key.isConnectable()) { // �ͻ��������Ϸ�����
			NioClientChannel clientChannel = (NioClientChannel) key.attachment();
			SocketChannel clientSocket = clientChannel.getSocket();
			
			try {
				if(clientSocket.isConnectionPending()) { // ������ڳ�������
					// ���������ӳɹ�Ϊֹ
					clientSocket.finishConnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				clientSocket.write(ByteBuffer.wrap("clienthello".getBytes()));
				clientSocket.register(getSelector(), SelectionKey.OP_READ);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// ����nio����ͨ��
			NioWorkerChannel workerChannel = new NioWorkerChannel(clientSocket);
			// ��ʼ����(�ͻ��˶�д����)
			workerChannel.work();
		}
	}
}
