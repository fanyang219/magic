package com.sky.magic.example.simple;

import java.net.InetSocketAddress;

import com.sky.magic.bootstrap.ClientBootstrap;
import com.sky.magic.channel.ChannelEvent;
import com.sky.magic.channel.Channels;
import com.sky.magic.channel.nio.NioClientChannelFactory;
import com.sky.magic.handler.ChannelHandler;
import com.sky.magic.handler.chain.ChannelChain;
import com.sky.magic.handler.chain.ChannelChainFactory;
import com.sky.magic.util.Constants;

/**
 * 简易客户端 发送数据:中文或英文数据
 * 
 * @author xufan
 */
public class SimpleClient {
	private static final String TAG = SimpleClient.class.getSimpleName();

	public void start() {
		// 配置数据通道
		ClientBootstrap bootstrap = new ClientBootstrap(
				new NioClientChannelFactory());
		// 配置数据处理器
		bootstrap.setChainFactory(new SimpleChannelFactory());
		// 连接到服务端
		bootstrap.connect(new InetSocketAddress(Constants.SERVER_HOST,
				Constants.SERVER_PORT));
	}

	public static void main(String args[]) {
		new SimpleClient().start();
	}

	class SimpleChannelFactory implements ChannelChainFactory {
		public ChannelChain getChannelChain() {
			ChannelChain chain = Channels.newChannelChain();

			// 配置数据处理链
			chain.addLast("client", new ClientChannelHandler());

			return chain;
		}
	}

	class ClientChannelHandler implements ChannelHandler {
		public boolean handleEvent(ChannelEvent event) {
			return false;
		}
	}
}
