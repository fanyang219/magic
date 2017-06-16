package com.sky.magic.handler.sink;

import com.sky.magic.channel.ChannelEvent;
import com.sky.magic.util.MLog;

public class EmptyChannelSink implements ChannelSink {
	private static final String TAG = EmptyChannelSink.class.getSimpleName();
	
	public void handleSink(ChannelEvent event) {
		MLog.log(TAG, "Enter empty channel sink, so do nothing.");
	}
}
