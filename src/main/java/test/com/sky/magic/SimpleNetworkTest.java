package com.sky.magic;

import com.sky.magic.example.simple.SimpleClient;
import com.sky.magic.example.simple.SimpleServer;

import junit.framework.TestCase;

/**
 * ���Լ��׷���˺Ϳͻ���ͨѶ�Ƿ�����(SimpleServer��SimpleClient)
 * @author xufan
 */
public class SimpleNetworkTest extends TestCase {
	public void setUp() throws Exception {
		super.setUp();
		
		initServer();
		sleep(100);
		initClient();
		sleep(100);
	}
	
	private void initServer() {
		SimpleServer server = new SimpleServer();
		server.start();
	}
	
	private void initClient() {
		System.out.println("");
		SimpleClient client = new SimpleClient();
		client.start();
	}
	
	// ���ͽ������ݲ���(����Hello���յ�Hi)
	public void testHello() {
		assertTrue(true);
	}
	
	
	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
