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
 * 简易服务端
 * 返回数据:客户端发送的数据是否通过校验(英文合法、中文非法)
 * @author xufan
 */
public class SimpleServer {
	private static final String TAG = SimpleServer.class.getSimpleName();
	private static final int SERVER_PORT = 8010;
	
	public void start() {
		// 配置数据通道
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerChannelFactory());
		// 配置数据处理器
		bootstrap.setChainFactory(new ChannelChainFactory() {
			public ChannelChain getChannelChain() {
				ChannelChain chain = Channels.newChannelChain();
				
//				chain.addFirst("test", new TestServerHandler());
				chain.addLast("simple", new SimpleServerHandler());
				
				return chain;
			}
		});
		// 绑定服务端口
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
