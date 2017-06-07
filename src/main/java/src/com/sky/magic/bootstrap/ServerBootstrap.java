package com.sky.magic.bootstrap;

import java.net.InetSocketAddress;

import com.sky.magic.channel.Channel;
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
		if(address==null) { // ��������
			throw new NullPointerException("address");
		}
		
		// ����server channel
		Channel channel = getFactory().createChannel();
		// ���ô��������������ڿ���ڲ�ʹ��
		channel.getConfig().setChainFactory(getChainFactory());
		// �󶨷��񣬿�ʼ����(���տͻ�������)
		channel.bind(address);
	}
}
