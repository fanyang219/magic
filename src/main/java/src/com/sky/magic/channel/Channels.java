package com.sky.magic.channel;

import com.sky.magic.handler.ChannelChain;
import com.sky.magic.handler.chain.DefaultChannelChain;

public final class Channels {
	public static ChannelChain newChannelChain() {
		return new DefaultChannelChain();
	}
}
