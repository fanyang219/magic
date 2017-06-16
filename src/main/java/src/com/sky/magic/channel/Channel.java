package com.sky.magic.channel;

import java.net.InetSocketAddress;

import com.sky.magic.handler.chain.ChannelChain;

/**
 * 数据通道接口类
 * @author xufan
 */
public interface Channel {
	void bind(InetSocketAddress address);
	
	void connect(InetSocketAddress address);
	
	void loopEvent();
	
	void write(String message);
	
	void setChain(ChannelChain chain);
	
	ChannelChain getChain();
	
	ChannelConfig getConfig();
}
