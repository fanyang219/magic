package com.sky.magic.example.simple;

import com.sky.magic.bootstrap.ServerBootstrap;
import com.sky.magic.channel.nio.NioServerChannelFactory;

/**
 * ���׷����
 * ��������:�ͻ��˷��͵������Ƿ�ͨ��У��(Ӣ�ĺϷ������ķǷ�)
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
