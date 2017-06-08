package com.sky.magic.channel;

import com.sky.magic.handler.chain.ChannelChain;
import com.sky.magic.handler.chain.DefaultChannelChain;

public final class Channels {
	public static ChannelChain newChannelChain() {
		return new DefaultChannelChain();
	}
}
