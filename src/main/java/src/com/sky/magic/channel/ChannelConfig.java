package com.sky.magic.channel;

import com.sky.magic.handler.chain.ChannelChainFactory;

// �洢һЩ������Ϣ���������ڲ���ȡ
public interface ChannelConfig {
	ChannelChainFactory getChainFactory();
	
	void setChainFactory(ChannelChainFactory chainFactory);
}
