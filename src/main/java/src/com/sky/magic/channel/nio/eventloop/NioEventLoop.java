package com.sky.magic.channel.nio.eventloop;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

import com.sky.magic.channel.ChannelException;
import com.sky.magic.channel.EventLoop;
import com.sky.magic.util.MLog;

/**
 * NIO事件处理器抽象类
 * @author xufan
 */
public abstract class NioEventLoop implements EventLoop {
	private static final String TAG = NioEventLoop.class.getSimpleName();
	
	private Selector selector = null;
	private String name = "default-loop";
	
	public NioEventLoop(String name) {
		this.name = name;
	}

	public void run() {
		while(true) { // 循环
			// 等待事件
			waitEvents();
			// 事件
			processEvents();
		}
	}
	
	private void waitEvents() {
		MLog.log(TAG, "Start to wait for events.", getName());
		
		try {
			getSelector().select();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void processEvents() {
		Iterator keysIter = getSelector().selectedKeys().iterator();
		while(keysIter.hasNext()) {
			SelectionKey key = (SelectionKey) keysIter.next();
			keysIter.remove();
			
			processEvent(key);
		}
	}
	
	public abstract void processEvent(SelectionKey key);
	
	public Selector getSelector() {
		if(selector==null) {
			try {
				selector = Selector.open();
			} catch (IOException e) {
				throw new ChannelException("Failed to open a selector.", e);
			}
		}
		return selector;
	}
	
	public String getName() {
		return name;
	}
}
