package com.sky.magic.example.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class TestServer {
	// 通道管理器
	private Selector selector = null;

	public void run() throws IOException {
		init();
		listen();
	}

	private void init() throws IOException {
		System.out.println("Server init");
		// 获取一个ServerSocket通道
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		// 设置通道为非阻塞
		serverChannel.configureBlocking(false);
		// 将该通道对应的ServerSocket绑定到端口
		serverChannel.socket().bind(new InetSocketAddress(8010));
		// 获取一个通道管理器
		selector = Selector.open();
		// 注册Accept事件
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
	}

	private void listen() throws IOException {
		System.out.println("Server listen");
		// 轮询访问selector
		while (true) {
			// 当注册的事件到达时，方法返回;否则，该方法会一直阻塞
			selector.select();
			// 获取selector选中项迭代器，选中项为注册的事件
			Iterator iter = selector.selectedKeys().iterator();
			while (iter.hasNext()) {
				SelectionKey key = (SelectionKey) iter.next();
				// 删除已选的key，防止重复处理
				iter.remove();
				// 客户端请求连接事件
				if (key.isAcceptable()) {
					ServerSocketChannel serverChannel = (ServerSocketChannel) key
							.channel();
					// 获取和客户端连接的通道
					SocketChannel channel = serverChannel.accept();
					// 设置为非阻塞
					channel.configureBlocking(false);
					// 给客户端发送消息
					channel.write(ByteBuffer.wrap(new String("You ok.")
							.getBytes()));
					// 和客户端连接成功以后，设置读权限，可以接收客户端信息
					channel.register(selector, SelectionKey.OP_READ);
				} else if (key.isReadable()) {
					read(key);
				}
			}
		}
	}

	private void read(SelectionKey key) throws IOException {
		// 获取消息通道
		SocketChannel clientChannel = (SocketChannel) key.channel();
		// 创建读取的缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(100);
		clientChannel.read(buffer);
		byte[] data = buffer.array();
		String msg = new String(data).trim();
		System.out.println("Server receive msg:" + msg);

//		ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
//		clientChannel.write(outBuffer);
	}

	public static void main(String args[]) throws IOException {
		new TestServer().run();
	}
}
