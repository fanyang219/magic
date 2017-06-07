package com.sky.magic.example.simple;

import com.sky.magic.bootstrap.ServerBootstrap;
import com.sky.magic.channel.ChannelEvent;
import com.sky.magic.channel.Channels;
import com.sky.magic.channel.nio.NioServerChannelFactory;
import com.sky.magic.handler.ChannelChain;
import com.sky.magic.handler.EmptyChannelHandler;
import com.sky.magic.handler.chain.ChannelChainFactory;
import com.sky.magic.util.MLog;

/**
 * ���׷����
 * ��������:�ͻ��˷��͵������Ƿ�ͨ��У��(Ӣ�ĺϷ������ķǷ�)
 * @author xufan
 */
public class SimpleServer {
	private static final String TAG = SimpleServer.class.getSimpleName();
	private static final int SERVER_PORT = 8010;
	
	public void start() {
		// ��������ͨ��
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerChannelFactory());
		// �������ݴ�����
		bootstrap.setChainFactory(new ChannelChainFactory() {
			public ChannelChain getChannelChain() {
				ChannelChain chain = Channels.newChannelChain();
				
//				chain.addFirst("test", new TestServerHandler());
				chain.addLast("simple", new SimpleServerHandler());
				
				return chain;
			}
		});
		// �󶨷���˿�
		bootstrap.bind(SERVER_PORT);
	}
	
	public static void main(String args[]) {
		new SimpleServer().start();
	}
	
	class TestServerHandler extends EmptyChannelHandler {
		public void handleEvent(ChannelEvent event) {
			MLog.log(TAG, "Server handle event in test.******");
		}
	}
	
	class SimpleServerHandler extends EmptyChannelHandler {
		public void handleEvent(ChannelEvent event) {
			MLog.log(TAG, "Server handle event in simple.******");
		}
	}
}
