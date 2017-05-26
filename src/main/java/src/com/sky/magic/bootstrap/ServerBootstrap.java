package com.sky.magic.bootstrap;

import java.net.InetSocketAddress;

import com.sky.magic.channel.ChannelFactory;
import com.sky.magic.util.MLog;

/**
 * �����ͨѶ��ʼ����
 * @author xufan
 */
public class ServerBootstrap extends Bootstrap {
	private static final String TAG = ServerBootstrap.class.getSimpleName();
	
	public ServerBootstrap(ChannelFactory factory) {
		super(factory);
		MLog.log(TAG, "1.Config channel factory.", "server-start");
	}
	
	public void bind(int port) {
		bind(new InetSocketAddress(port));
	}
	
	/**
	 * ����˰�����ͨѶ�˿�
	 * @param address ����ͨѶ��ַ
	 */
	public void bind(InetSocketAddress address) {
		MLog.log(TAG, "2.Bind server port:"+address.getPort(), "server-start");
		// TODO Auto-generated method stub
	}
}
