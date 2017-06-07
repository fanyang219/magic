package com.sky.magic.channel.nio.config;

import com.sky.magic.channel.ChannelConfig;
import com.sky.magic.handler.chain.ChannelChainFactory;

public class DefaultChannelConfig implements ChannelConfig {
	private ChannelChainFactory chainFactory = null;
	
	public ChannelChainFactory getChainFactory() {
		return chainFactory;
	}

	public void setChainFactory(ChannelChainFactory chainFactory) {
		if(chainFactory==null) {
			throw new NullPointerException("chainFactory");
		}
		this.chainFactory = chainFactory;
	}
}
