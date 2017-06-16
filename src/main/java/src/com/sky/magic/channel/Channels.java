package com.sky.magic.channel;

import com.sky.magic.channel.event.MessageEvent;
import com.sky.magic.handler.chain.ChannelChain;
import com.sky.magic.handler.chain.DefaultChannelChain;

public final class Channels {
	public static ChannelChain newChannelChain() {
		return new DefaultChannelChain();
	}

	// ͨ��channelд������
	public static void write(Channel channel, String message) {
		// ������Ϣ�¼�
		MessageEvent event = new MessageEvent(channel, message);
		// ͨ��chain����֮���ٷ���
		channel.getChain().handleDownstream(event);
	}
}
