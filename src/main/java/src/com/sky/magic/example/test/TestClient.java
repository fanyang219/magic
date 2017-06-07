package com.sky.magic.example.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class TestClient {
	// ͨ��������
	private Selector selector = null;

	private void run() throws IOException {
		init();
		listen();
	}

	private void init() throws IOException {
		System.out.println("Client init");

		// ��ȡSocketͨ��
		SocketChannel channel = SocketChannel.open();
		// ����ͨ��������
		channel.configureBlocking(false);
		// ��ȡSelector
		selector = Selector.open();
		// �ͻ������ӷ�����
		channel.connect(new InetSocketAddress("localhost", 8010));
		// ע�������¼�
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
		// ��ȡ��Ϣͨ��
		SocketChannel channel = (SocketChannel) key.channel();
		// ������ȡ�Ļ�����
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
