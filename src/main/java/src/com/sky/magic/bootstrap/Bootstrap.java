package com.sky.magic.bootstrap;

import com.sky.magic.channel.ChannelFactory;
import com.sky.magic.handler.chain.ChannelChain;
import com.sky.magic.handler.chain.ChannelChainFactory;

/**
 * �����ʼ����
 * ְ�𣺲�������ͨ��(channel)
 * @author xufan
 */
public class Bootstrap {
	private static final String TAG = Bootstrap.class.getSimpleName();
	
	private ChannelFactory factory = null;
	private ChannelChainFactory chainFactory = null;
	
	/**
	 * ͨ����������������ͨ������,����ͨ���������Դ�������ͨ��
	 * @param factory
	 */
	public Bootstrap(ChannelFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * ��ȡ����ͨ������
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
	
	public ChannelChain getChannelChain() {
		if(chainFactory==null) {
			return null;
		}
		return getChainFactory().getChannelChain();
	}
	
	public void setChainFactory(ChannelChainFactory chainFactory) {
		if(chainFactory==null) {
			throw new NullPointerException("chainFactory");
		}
		
//		MLog.log(TAG, "Config handler-chain factory.", "config-chain");
		this.chainFactory = chainFactory;
	}
}
