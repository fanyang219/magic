package com.sky.magic.bootstrap;

import com.sky.magic.channel.ChannelFactory;

/**
 * 服务端通讯初始化类
 * @author xufan
 */
public class ServerBootstrap extends Bootstrap {
	private static final String TAG = ServerBootstrap.class.getSimpleName();
	
	public ServerBootstrap(ChannelFactory factory) {
		super(factory);
	}
}
