package com.sky.magic.bootstrap;

import java.net.InetSocketAddress;

import com.sky.magic.channel.ChannelFactory;
import com.sky.magic.util.MLog;

/**
 * 客户端通讯配置类
 * @author xufan
 */
public class ClientBootstrap extends Bootstrap {
	private static final String TAG = ClientBootstrap.class.getSimpleName();
	
	public ClientBootstrap(ChannelFactory factory) {
		super(factory);
		MLog.log(TAG, "1.Config channel factory.", "client-connect");
	}

	public void connect(InetSocketAddress address) {
		MLog.log(TAG, "2.Connect to server host:"+address.getHostName()+", port:"+address.getPort(), "client-connect");
		// TODO Auto-generated method stub
	}
}
