package com.sky.magic.channel;

import java.net.InetSocketAddress;

import com.sky.magic.channel.nio.config.DefaultChannelConfig;
import com.sky.magic.handler.chain.ChannelChain;

public abstract class AbstractChannel implements Channel {
	private ChannelChain chain = null;
	private ChannelConfig config = null;
	
	public void bind(InetSocketAddress address) {
		// TODO Auto-generated method stub
	}
	
	public void connect(InetSocketAddress address) {
		// TODO Auto-generated method stub
	}
	
	public void write(String message) {
		Channels.write(this, message);
	}
	
	public ChannelChain getChain() {
		if(chain==null) {
			chain = Channels.newChannelChain();
		}
		return chain;
	}

	public void setChain(ChannelChain chain) {
		this.chain = chain;
	}

	public ChannelConfig getConfig() {
		if(config==null) {
			config = new DefaultChannelConfig();
		}
		return config;
	}

	public void setConfig(ChannelConfig config) {
		this.config = config;
	}
}
