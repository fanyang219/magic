package com.sky.magic.channel;

import java.nio.channels.Selector;

/**
 * 事件分发器接口
 * 职责：循环分发事件
 * author xufan
 */
public interface EventLoop extends Runnable {
	Selector getSelector();
}
