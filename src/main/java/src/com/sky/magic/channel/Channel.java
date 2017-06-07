package com.sky.magic.channel;

import java.net.InetSocketAddress;

import com.sky.magic.handler.ChannelChain;

/**
 * ����ͨ���ӿ���
 * @author xufan
 */
public interface Channel {
	void bind(InetSocketAddress address);
	
	void connect(InetSocketAddress address);
	
	void loopEvent();
	
	void setChain(ChannelChain chain);
	
	ChannelChain getChain();
	
	ChannelConfig getConfig();
}
