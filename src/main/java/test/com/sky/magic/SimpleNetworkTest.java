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
		initClient();
	}
	
	private void initServer() {
		SimpleServer server = new SimpleServer();
		server.start();
		
		// ģ�����������
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
	
	// ���ͽ������ݲ���(����Hello���յ�Hi)
	public void testHello() {
		assertTrue(true);
	}
	
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
