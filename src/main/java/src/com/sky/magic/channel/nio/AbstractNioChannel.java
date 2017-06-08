package com.sky.magic.channel.nio;

import com.sky.magic.channel.AbstractChannel;
import com.sky.magic.channel.nio.eventloop.EventLoop;

public abstract class AbstractNioChannel extends AbstractChannel {
	private EventLoop eventLoop = null;
	
	public void loopEvent() {
		// �첽�����¼�ѭ��
		new Thread(getEventLoop()).start();
	}
	
	public EventLoop getEventLoop() {
		if(eventLoop==null) {
			eventLoop = createEventLoop();
		}
		return eventLoop;
	}
	
	public abstract EventLoop createEventLoop();
}
