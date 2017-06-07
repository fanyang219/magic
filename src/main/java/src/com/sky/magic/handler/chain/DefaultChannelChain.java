package com.sky.magic.handler.chain;

import java.util.HashMap;
import java.util.Map;

import com.sky.magic.channel.ChannelEvent;
import com.sky.magic.handler.ChannelHandler;
import com.sky.magic.handler.ChannelChain;
import com.sky.magic.util.MLog;

/**
 * ���ݴ��������(������ģʽ)
 * ְ�𣺹��������ݴ��������Զ��崦���������༰˳��
 * ���Է������չ���ݴ���ҵ��
 * @author xufan
 */
public class DefaultChannelChain implements ChannelChain {
	private static final String TAG = DefaultChannelChain.class.getSimpleName();
	
	private Map<String, ChannelHandler> handlerMap = null;
	private ChannelHandler headHandler = null;
	private ChannelHandler tailHandler = null;
	
	public void addFirst(String name, ChannelHandler handler) {
		if(headHandler==null || tailHandler==null) { // ��������
			headHandler = handler;
			tailHandler = handler;
			getHandlerMap().put(name, handler);
			return;
		}
		
		handler.setNextHandler(headHandler);
		headHandler.setPrevHandler(handler);
		
		getHandlerMap().put(name, handler);
	}
	
	public void addLast(String name, ChannelHandler handler) {
		if(headHandler==null || tailHandler==null) { // ��������
			headHandler = handler;
			tailHandler = handler;
			getHandlerMap().put(name, handler);
			return;
		}
		
		tailHandler.setNextHandler(handler);
		handler.setPrevHandler(tailHandler);
		
		getHandlerMap().put(name, handler);
	}

	public void addBefore(String baseName, String name, ChannelHandler handler) {
		// TODO name�Ѵ���
		if(headHandler==null || tailHandler==null) { // ��������
			addFirst(name, handler);
			return;
		}
		
		ChannelHandler baseHandler = getHandlerMap().get(baseName);
		if(baseHandler==null) { // �����ڲ���handler
			return;
		}
		
		if(baseHandler==headHandler) { // ����handler��ͷ��
			addFirst(name, handler);
			return;
		}
		
		// ��handler����baseHandlerǰ��
		// �������ǰ��Ľڵ�󶨹�ϵ
		baseHandler.getPrevHandler().setNextHandler(handler);
		handler.setPrevHandler(baseHandler.getPrevHandler());
		// �������󶨹�ϵ
		handler.setNextHandler(baseHandler);
		baseHandler.setPrevHandler(handler);
		
		getHandlerMap().put(name, handler);
	}

	public void addAfter(String baseName, String name, ChannelHandler handler) {
		// TODO name�Ѵ���
		if(headHandler==null || tailHandler==null) { // ��������
			addLast(name, handler);
			return;
		}
		
		ChannelHandler baseHandler = getHandlerMap().get(baseName);
		if(baseHandler==null) { // �����ڲ���handler
			return;
		}
		
		if(baseHandler==tailHandler) { // ����handler��ͷ��
			addLast(name, handler);
			return;
		}
		
		// ��handler����baseHandlerǰ��
		// ����������Ľڵ�󶨹�ϵ
		baseHandler.getNextHandler().setPrevHandler(handler);
		handler.setNextHandler(baseHandler.getNextHandler());
		// �������󶨹�ϵ
		handler.setPrevHandler(baseHandler);
		baseHandler.setNextHandler(handler);
		
		getHandlerMap().put(name, handler);
	}
	
	public void clear() {
		getHandlerMap().clear();
		
		handlerMap = null;
		headHandler = null;
		tailHandler = null;
	}

	public ChannelHandler getChannelHandler(String name) {
		return getHandlerMap().get(name);
	}
	
	private Map<String, ChannelHandler> getHandlerMap() {
		if(handlerMap==null) {
			handlerMap = new HashMap<String, ChannelHandler>();
		}
		return handlerMap;
	}

	// ����ͨ���¼�
	public void handleEvent(ChannelEvent event) {
		if(headHandler==null || tailHandler==null) {
			MLog.log(TAG, "The chain contains no handlers, discarding:"+event);
			return;
		}
		// ��ʽ�����¼�(��ǰ������ִ����Ϻ��Զ�������һ��������)
		headHandler.handleEvent(event);
	}
}
