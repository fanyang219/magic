package com.sky.magic.bootstrap;

import com.sky.magic.channel.ChannelFactory;

/**
 * �����ʼ����
 * ְ�𣺲�������ͨ��(channel)
 * @author xufan
 */
public class Bootstrap {
	private static final String TAG = Bootstrap.class.getSimpleName();
	
	private ChannelFactory factory = null;
	
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
			throw new IllegalStateException("factory has not been set.");
		}
		return factory;
	}
}
