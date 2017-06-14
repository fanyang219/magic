package com.sky.magic.example.simple;

import com.sky.magic.bootstrap.ServerBootstrap;
import com.sky.magic.channel.ChannelEvent;
import com.sky.magic.channel.Channels;
import com.sky.magic.channel.event.MessageEvent;
import com.sky.magic.channel.nio.NioServerChannelFactory;
import com.sky.magic.handler.ChannelHandler;
import com.sky.magic.handler.chain.ChannelChain;
import com.sky.magic.handler.chain.ChannelChainFactory;
import com.sky.magic.util.Constants;
import com.sky.magic.util.MLog;

/**
 * ���׷����
 * ��������:�ͻ��˷��͵������Ƿ�ͨ��У��(Ӣ�ĺϷ������ķǷ�)
 * @author xufan
 */
public class SimpleServer {
	private static final String TAG = SimpleServer.class.getSimpleName();
	
	public void start() {
		// ��������ͨ��
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerChannelFactory());
		// �������ݴ�����
		bootstrap.setChainFactory(new ChannelChainFactory() {
			public ChannelChain getChannelChain() {
				ChannelChain chain = Channels.newChannelChain();
				
				chain.addLast("message", new MessageServerHandler());
				chain.addLast("test", new TestServerHandler());
				
				return chain;
			}
		});
		// �󶨷���˿�
		bootstrap.bind(Constants.SERVER_PORT);
	}
	
	public static void main(String args[]) {
		new SimpleServer().start();
	}
	
	class TestServerHandler implements ChannelHandler {
		public boolean handleEvent(ChannelEvent event) {
			MLog.log(TAG, "Server handle event in test.******");
			return false;
		}
	}
	
	class MessageServerHandler implements ChannelHandler {
		public boolean handleEvent(ChannelEvent event) {
			MLog.log(TAG, "Server handle event in message.******");
			if(event instanceof MessageEvent) {
				MessageEvent messageEvent = (MessageEvent) event;
				MLog.log(TAG, "Handle message:"+messageEvent.getMessage());
			}
			return true;
		}
	}
}
