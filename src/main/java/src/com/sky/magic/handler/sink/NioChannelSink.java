package com.sky.magic.handler.sink;

import com.sky.magic.channel.ChannelEvent;
import com.sky.magic.util.Constants;
import com.sky.magic.util.MLog;

public class NioChannelSink implements ChannelSink {
	private static final String TAG = NioChannelSink.class.getSimpleName(); 
	
	public void handleSink(ChannelEvent event) {
		MLog.log(TAG, "handle event in sink.", Constants.LOG_WRITE_DATA);
	}
}
