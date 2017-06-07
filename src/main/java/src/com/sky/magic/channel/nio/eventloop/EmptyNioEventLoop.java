package com.sky.magic.channel.nio.eventloop;

import java.nio.channels.SelectionKey;

public class EmptyNioEventLoop extends NioEventLoop {
	public EmptyNioEventLoop() {
		super("empty-loop");
	}

	@Override
	public void processEvent(SelectionKey key) {
		// ø’ µœ÷
	}
}
