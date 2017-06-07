package com.sky.magic.channel;

import com.sky.magic.handler.chain.ChannelChainFactory;

// 存储一些配置信息，方便框架内部获取
public interface ChannelConfig {
	ChannelChainFactory getChainFactory();
	
	void setChainFactory(ChannelChainFactory chainFactory);
}
