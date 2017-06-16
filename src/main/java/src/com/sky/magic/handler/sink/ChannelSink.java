package com.sky.magic.handler.sink;

import com.sky.magic.channel.ChannelEvent;

/**
 * 系统内部事件处理器，本可以实现ChannelHandler，但另起接口更加清晰
 * 职责：在通过handler业务处理后，再通过sink写数据
 * @author xufan
 */
public interface ChannelSink {
	void handleSink(ChannelEvent event);
}
