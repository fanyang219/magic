package com.sky.magic.example.simple;

import com.sky.magic.bootstrap.ServerBootstrap;
import com.sky.magic.channel.nio.NioServerChannelFactory;

/**
 * 简易服务端
 * 返回数据:客户端发送的数据是否通过校验(英文合法、中文非法)
 * @author xufan
 */
public class SimpleServer {
	private static final int SERVER_PORT = 8010;
	
	public void start() {
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerChannelFactory());
		bootstrap.bind(SERVER_PORT);
	}
	
	public static void main(String args[]) {
		new SimpleServer().start();
	}
}
