package com.sky.magic.handler.sink;

import com.sky.magic.channel.ChannelEvent;

/**
 * ϵͳ�ڲ��¼���������������ʵ��ChannelHandler��������ӿڸ�������
 * ְ����ͨ��handlerҵ�������ͨ��sinkд����
 * @author xufan
 */
public interface ChannelSink {
	void handleSink(ChannelEvent event);
}
