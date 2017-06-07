package com.sky.magic.bootstrap;

import java.net.InetSocketAddress;

import com.sky.magic.channel.Channel;
import com.sky.magic.channel.ChannelFactory;
import com.sky.magic.util.MLog;

/**
 * 服务端通讯初始化类
 * @author xufan
 */
public class ServerBootstrap extends Bootstrap {
	private static final String TAG = ServerBootstrap.class.getSimpleName();
	
	public ServerBootstrap(ChannelFactory factory) {
		super(factory);
		MLog.log(TAG, "1.Config channel factory.", "server-start");
	}
	
	public void bind(int port) {
		bind(new InetSocketAddress(port));
	}
	
	/**
	 * 服务端绑定数据通讯端口
	 * @param address 数据通讯地址
	 */
	public void bind(InetSocketAddress address) {
		MLog.log(TAG, "2.Bind server port:"+address.getPort(), "server-start");
		if(address==null) { // 保护处理
			throw new NullPointerException("address");
		}
		
		// 创建server channel
		Channel channel = getFactory().createChannel();
		// 设置处理器工厂，便于框架内部使用
		channel.getConfig().setChainFactory(getChainFactory());
		// 绑定服务，开始工作(接收客户端连接)
		channel.bind(address);
	}
}
