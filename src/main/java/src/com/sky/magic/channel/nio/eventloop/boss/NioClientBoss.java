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
 * 客户端事件分发器
 * @author xufan
 */
public class NioClientBoss extends NioEventLoop implements Boss {
	private static final String TAG = NioClientBoss.class.getSimpleName();
	
	public NioClientBoss() {
		super("client-boss");
	}

	public void processEvent(SelectionKey key) {
		MLog.log(TAG, "Start to process single event.", getName());
		if(key.isConnectable()) { // 客户端连接上服务器
			NioClientChannel clientChannel = (NioClientChannel) key.attachment();
			SocketChannel clientSocket = clientChannel.getSocket();
			
			try {
				if(clientSocket.isConnectionPending()) { // 如果正在尝试连接
					// 阻塞到连接成功为止
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
			
			// 创建nio工作通道
			NioWorkerChannel workerChannel = new NioWorkerChannel(clientSocket);
			// 开始工作(客户端读写数据)
			workerChannel.work();
		}
	}
}
