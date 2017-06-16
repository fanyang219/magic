package com.sky.magic.channel;

import com.sky.magic.channel.event.MessageEvent;
import com.sky.magic.handler.chain.ChannelChain;
import com.sky.magic.handler.chain.DefaultChannelChain;

public final class Channels {
	public static ChannelChain newChannelChain() {
		return new DefaultChannelChain();
	}

	// 通过channel写入数据
	public static void write(Channel channel, String message) {
		// 生成消息事件
		MessageEvent event = new MessageEvent(channel, message);
		// 通过chain处理之后再发送
		channel.getChain().handleDownstream(event);
	}
}
