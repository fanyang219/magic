package com.sky.magic.example.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class TestClient {
	// 通道管理器
	private Selector selector = null;

	private void run() throws IOException {
		init();
		listen();
	}

	private void init() throws IOException {
		System.out.println("Client init");

		// 获取Socket通道
		SocketChannel channel = SocketChannel.open();
		// 设置通道非阻塞
		channel.configureBlocking(false);
		// 获取Selector
		selector = Selector.open();
		// 客户端连接服务器
		channel.connect(new InetSocketAddress("localhost", 8010));
		// 注册连接事件
		channel.register(selector, SelectionKey.OP_CONNECT);
	}

	private void listen() throws IOException {
		System.out.println("Client listen");
		while (true) {
			selector.select();
			System.out.println("Client process");
			Iterator iter = selector.selectedKeys().iterator();
			while (iter.hasNext()) {
				SelectionKey key = (SelectionKey) iter.next();
				iter.remove();
				if (key.isConnectable()) {
					SocketChannel channel = (SocketChannel) key.channel();
					if (channel.isConnectionPending()) {
						channel.finishConnect();
					}
//					channel.configureBlocking(false);
//
					channel.write(ByteBuffer.wrap("Client Connect OK.".getBytes()));
					channel.register(selector, SelectionKey.OP_READ);
				} else if (key.isReadable()) {
					read(key);
				}
			}
		}
	}

	private void read(SelectionKey key) throws IOException {
		// 获取消息通道
		SocketChannel channel = (SocketChannel) key.channel();
		// 创建读取的缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(100);
		channel.read(buffer);
		byte[] data = buffer.array();
		String msg = new String(data).trim();
		System.out.println("Client receive msg:" + msg);

//		ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
//		channel.write(outBuffer);
	}

	public static void main(String args[]) throws IOException {
		new TestClient().run();
	}
}
