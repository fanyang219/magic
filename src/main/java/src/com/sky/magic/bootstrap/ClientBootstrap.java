package com.sky.magic.bootstrap;

import java.net.InetSocketAddress;

import com.sky.magic.channel.ChannelFactory;

/**
 * 客户端通讯初始化类
 * @author xufan
 */
public class ClientBootstrap extends Bootstrap {
	private static final String TAG = ClientBootstrap.class.getSimpleName();
	
	public ClientBootstrap(ChannelFactory factory) {
		super(factory);
	}

	public void connect(InetSocketAddress address) {
		// TODO Auto-generated method stub
	}
}
