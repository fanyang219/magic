package com.sky.magic;

import com.sky.magic.bootstrap.ClientBootstrap;
import com.sky.magic.bootstrap.ServerBootstrap;
import com.sky.magic.channel.Channel;
import com.sky.magic.channel.ChannelFactory;

import junit.framework.TestCase;

public class BootstrapTest extends TestCase {
	private static final String TAG = BootstrapTest.class.getSimpleName();
	
	private ServerBootstrap serverBootstrap = null;
	private ClientBootstrap clientBootstrap = null;
	
	public void setUp() throws Exception {
		super.setUp();
		
		serverBootstrap = new ServerBootstrap(new ChannelFactory(){
			public Channel createChannel() {
				// TODO Auto-generated method stub
				return null;
			}});
		
		clientBootstrap = new ClientBootstrap(new ChannelFactory() {
			public Channel createChannel() {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}
	
	public void testServerBootstrap() {
		assertTrue(true);
	}
	
	public void testClientBootstrap() {
		assertTrue(true);
	}
	
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
