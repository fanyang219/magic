package com.sky.magic.channel;

import java.nio.channels.Selector;

/**
 * �¼��ַ����ӿ�
 * ְ��ѭ���ַ��¼�
 * author xufan
 */
public interface EventLoop extends Runnable {
	Selector getSelector();
}
