package com.sky.magic.channel;

/**
 * 数据通道工厂接口类
 * 职责：创建数据通道
 */
public interface ChannelFactory {
	Channel createChannel();
}
