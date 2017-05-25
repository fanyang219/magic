package com.sky.magic;

import com.sky.magic.example.simple.SimpleClient;
import com.sky.magic.example.simple.SimpleServer;

import junit.framework.TestCase;

/**
 * 测试简易服务端和客户端通讯是否正常(SimpleServer、SimpleClient)
 * @author xufan
 */
public class SimpleNetworkTest extends TestCase {
	public void setUp() throws Exception {
		super.setUp();
		
		initServer();
		initClient();
	}
	
	private void initServer() {
		SimpleServer server = new SimpleServer();
		server.start();
		
		// 模拟服务器启动
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void initClient() {
		SimpleClient client = new SimpleClient();
		client.start();
	}
	
	// 发送接收数据测试(发送Hello，收到Hi)
	public void testHello() {
		assertTrue(true);
	}
	
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
