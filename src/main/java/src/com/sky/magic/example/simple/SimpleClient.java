package com.sky.magic.example.simple;

import java.net.InetSocketAddress;

import com.sky.magic.bootstrap.ClientBootstrap;
import com.sky.magic.channel.ChannelEvent;
import com.sky.magic.channel.Channels;
import com.sky.magic.channel.nio.NioClientChannelFactory;
import com.sky.magic.handler.EmptyChannelHandler;
import com.sky.magic.handler.chain.ChannelChain;
import com.sky.magic.handler.chain.ChannelChainFactory;

/**
 * ���׿ͻ���
 * ��������:���Ļ�Ӣ������
 * @author xufan
 */
public class SimpleClient {
	private static final String TAG = SimpleClient.class.getSimpleName();
	private static final String SERVER_HOST = "127.0.0.1";
	private static final int SERVER_PORT = 8010;
	
	public void start() {
		// ��������ͨ��
		ClientBootstrap bootstrap = new ClientBootstrap(new NioClientChannelFactory());
		// �������ݴ�����
		bootstrap.setChainFactory(new SimpleChannelFactory());
		// ���ӵ������
		bootstrap.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
	}
	
	public static void main(String args[]) {
		new SimpleClient().start();
	}
	
	class SimpleChannelFactory implements ChannelChainFactory {
		public ChannelChain getChannelChain() {
			ChannelChain chain = Channels.newChannelChain();
			
			// �������ݴ�����
			chain.addLast("simple", new SimpleChannelHandler());
			
			return chain;
		}
	}
	
	class SimpleChannelHandler extends EmptyChannelHandler {
		@Override
		public void handleEvent(ChannelEvent event) {
			// TODO Auto-generated method stub
		}
	}
}
