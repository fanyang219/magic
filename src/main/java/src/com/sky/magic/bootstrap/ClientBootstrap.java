package com.sky.magic.bootstrap;

import java.net.InetSocketAddress;

import com.sky.magic.channel.Channel;
import com.sky.magic.channel.ChannelFactory;
import com.sky.magic.util.Constants;
import com.sky.magic.util.MLog;

/**
 * 客户端通讯配置类
 * @author xufan
 */
public class ClientBootstrap extends Bootstrap {
	private static final String TAG = ClientBootstrap.class.getSimpleName();
	
	public ClientBootstrap(ChannelFactory factory) {
		super(factory);
		MLog.log(TAG, "1.Config channel factory.", Constants.LOG_CLIENT_START);
	}

	public void connect(InetSocketAddress address) {
		MLog.log(TAG, "2.Connect to server host:"+address.getHostName()+", port:"+address.getPort(), Constants.LOG_CLIENT_START);
		if(address==null) { // 保护处理
			throw new NullPointerException("address");
		}
		
		// 创建client channel
		Channel channel = getFactory().createChannel();
		// 连接服务端口，开始工作(读写数据)
		channel.connect(address);
	}
}
