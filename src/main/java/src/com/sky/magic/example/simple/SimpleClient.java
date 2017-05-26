package com.sky.magic.example.simple;

import java.net.InetSocketAddress;

import com.sky.magic.bootstrap.ClientBootstrap;
import com.sky.magic.channel.nio.NioClientChannelFactory;

/**
 * 简易客户端
 * 发送数据:中文或英文数据
 * @author xufan
 */
public class SimpleClient {
	private static final String TAG = SimpleClient.class.getSimpleName();
	private static final String SERVER_HOST = "127.0.0.1";
	private static final int SERVER_PORT = 8010;
	
	public void connect() {
		ClientBootstrap bootstrap = new ClientBootstrap(new NioClientChannelFactory());
		bootstrap.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
	}
	
	public static void main(String args[]) {
		new SimpleClient().connect();
	}
}
