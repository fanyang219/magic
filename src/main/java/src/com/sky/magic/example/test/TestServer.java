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
	// ͨ��������
	private Selector selector = null;

	public void run() throws IOException {
		init();
		listen();
	}

	private void init() throws IOException {
		System.out.println("Server init");
		// ��ȡһ��ServerSocketͨ��
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		// ����ͨ��Ϊ������
		serverChannel.configureBlocking(false);
		// ����ͨ����Ӧ��ServerSocket�󶨵��˿�
		serverChannel.socket().bind(new InetSocketAddress(8010));
		// ��ȡһ��ͨ��������
		selector = Selector.open();
		// ע��Accept�¼�
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
	}

	private void listen() throws IOException {
		System.out.println("Server listen");
		// ��ѯ����selector
		while (true) {
			// ��ע����¼�����ʱ����������;���򣬸÷�����һֱ����
			selector.select();
			// ��ȡselectorѡ�����������ѡ����Ϊע����¼�
			Iterator iter = selector.selectedKeys().iterator();
			while (iter.hasNext()) {
				SelectionKey key = (SelectionKey) iter.next();
				// ɾ����ѡ��key����ֹ�ظ�����
				iter.remove();
				// �ͻ������������¼�
				if (key.isAcceptable()) {
					ServerSocketChannel serverChannel = (ServerSocketChannel) key
							.channel();
					// ��ȡ�Ϳͻ������ӵ�ͨ��
					SocketChannel channel = serverChannel.accept();
					// ����Ϊ������
					channel.configureBlocking(false);
					// ���ͻ��˷�����Ϣ
					channel.write(ByteBuffer.wrap(new String("You ok.")
							.getBytes()));
					// �Ϳͻ������ӳɹ��Ժ����ö�Ȩ�ޣ����Խ��տͻ�����Ϣ
					channel.register(selector, SelectionKey.OP_READ);
				} else if (key.isReadable()) {
					read(key);
				}
			}
		}
	}

	private void read(SelectionKey key) throws IOException {
		// ��ȡ��Ϣͨ��
		SocketChannel clientChannel = (SocketChannel) key.channel();
		// ������ȡ�Ļ�����
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
