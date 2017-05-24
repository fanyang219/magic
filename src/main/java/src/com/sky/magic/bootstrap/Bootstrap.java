package com.sky.magic.bootstrap;

import com.sky.magic.channel.ChannelFactory;

/**
 * 网络初始化类
 * 职责：操作数据通道(channel)
 * @author xufan
 */
public class Bootstrap {
	private static final String TAG = Bootstrap.class.getSimpleName();
	
	private ChannelFactory factory = null;
	
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
			throw new IllegalStateException("factory has not been set.");
		}
		return factory;
	}
}
