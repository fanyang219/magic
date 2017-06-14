package com.sky.magic.handler;

import com.sky.magic.channel.ChannelEvent;

/**
 * 数据处理器默认实现
 * @author xufan
 */
public class DefaultChannelHandler implements ChannelHandler {
	public boolean handleEvent(ChannelEvent event) {
		return false;
	}
}
