package com.sky.magic.bootstrap;

import java.net.InetSocketAddress;

import com.sky.magic.channel.ChannelFactory;

/**
 * �����ͨѶ��ʼ����
 * @author xufan
 */
public class ServerBootstrap extends Bootstrap {
	private static final String TAG = ServerBootstrap.class.getSimpleName();
	
	public ServerBootstrap(ChannelFactory factory) {
		super(factory);
	}
	
	public void bind(int port) {
		bind(new InetSocketAddress(port));
	}
	
	/**
	 * ����˰�����ͨѶ�˿�
	 * @param address ����ͨѶ��ַ
	 */
	public void bind(InetSocketAddress address) {
		// TODO Auto-generated method stub
	}
}
