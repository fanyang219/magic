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
 * 简易客户端
 * 发送数据:中文或英文数据
 * @author xufan
 */
public class SimpleClient {
	private static final String TAG = SimpleClient.class.getSimpleName();
	private static final String SERVER_HOST = "127.0.0.1";
	private static final int SERVER_PORT = 8010;
	
	public void start() {
		// 配置数据通道
		ClientBootstrap bootstrap = new ClientBootstrap(new NioClientChannelFactory());
		// 配置数据处理器
		bootstrap.setChainFactory(new SimpleChannelFactory());
		// 连接到服务端
		bootstrap.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
	}
	
	public static void main(String args[]) {
		new SimpleClient().start();
	}
	
	class SimpleChannelFactory implements ChannelChainFactory {
		public ChannelChain getChannelChain() {
			ChannelChain chain = Channels.newChannelChain();
			
			// 配置数据处理链
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
