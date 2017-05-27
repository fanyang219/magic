package com.sky.magic.bootstrap;

import com.sky.magic.channel.ChannelChainFactory;
import com.sky.magic.channel.ChannelFactory;
import com.sky.magic.util.MLog;

/**
 * 网络初始化类
 * 职责：操作数据通道(channel)
 * @author xufan
 */
public class Bootstrap {
	private static final String TAG = Bootstrap.class.getSimpleName();
	
	private ChannelFactory factory = null;
	private ChannelChainFactory chainFactory = null;
	
	/**
	 * 通过构造器设置数据通道工厂,后续通过工厂可以创建数据通道
	 * @param factory
	 */
	public Bootstrap(ChannelFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * 获取数据通道工厂
	 * @return
	 */
	public ChannelFactory getFactory() {
		if(factory==null) {
			throw new IllegalStateException("Factory has not been set.");
		}
		return factory;
	}
	
	public ChannelChainFactory getChainFactory() {
		return chainFactory;
	}
	
	public void setChainFactory(ChannelChainFactory chainFactory) {
		if(chainFactory==null) {
			throw new NullPointerException("chainFactory");
		}
		
		MLog.log(TAG, "Config handler-chain factory.", "config-chain");
		this.chainFactory = chainFactory;
	}
}
