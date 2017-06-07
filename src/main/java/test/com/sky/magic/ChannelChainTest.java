package com.sky.magic;

import com.sky.magic.handler.ChannelChain;
import com.sky.magic.handler.ChannelHandler;
import com.sky.magic.handler.DefaultChannelHandler;
import com.sky.magic.handler.EmptyChannelHandler;
import com.sky.magic.handler.chain.DefaultChannelChain;

import junit.framework.TestCase;

public class ChannelChainTest extends TestCase {
	private ChannelChain dataChain = null;
	
	private ChannelHandler oneHandler = null;
	private ChannelHandler twoHandler = null;
	private ChannelHandler theHandler = null;
	
	public void setUp() throws Exception {
		super.setUp();
		
		initChain();
	}
	
	private void initChain() {
		dataChain = new DefaultChannelChain();
		
		oneHandler = new DefaultChannelHandler();
		twoHandler = new DefaultChannelHandler();
		theHandler = new DefaultChannelHandler();
	}
	
	public void testAddFirst() {
		dataChain.addFirst("one", oneHandler);
		dataChain.addFirst("two", twoHandler);
		
		ChannelHandler handler1 = dataChain.getChannelHandler("one");
		assertTrue(handler1!=null);
		
		ChannelHandler handler2 = dataChain.getChannelHandler("two");
		assertTrue(handler2!=null);
		
		assertTrue(handler1.getPrevHandler().equals(handler2));
		assertTrue(handler1.getNextHandler() instanceof EmptyChannelHandler);
		assertTrue(handler2.getPrevHandler() instanceof EmptyChannelHandler);
		assertTrue(handler2.getNextHandler().equals(handler1));
	}
	
	public void testAddLast() {
		dataChain.clear();
		
		dataChain.addLast("one", oneHandler);
		dataChain.addLast("two", twoHandler);
		
		ChannelHandler handler1 = dataChain.getChannelHandler("one");
		assertTrue(handler1!=null);
		
		ChannelHandler handler2 = dataChain.getChannelHandler("two");
		assertTrue(handler2!=null);
		
		assertTrue(handler1.getPrevHandler() instanceof EmptyChannelHandler);
		assertTrue(handler1.getNextHandler().equals(handler2));
		assertTrue(handler2.getPrevHandler().equals(handler1));
		assertTrue(handler2.getNextHandler() instanceof EmptyChannelHandler);
	}
	
	public void testAddBefore() {
		dataChain.addFirst("one", oneHandler);
		dataChain.addLast("two", twoHandler);
		dataChain.addBefore("two", "the", theHandler);
		
		ChannelHandler handler1 = dataChain.getChannelHandler("one");
		assertTrue(handler1!=null);
		
		ChannelHandler handler2 = dataChain.getChannelHandler("two");
		assertTrue(handler2!=null);
		
		ChannelHandler handlert = dataChain.getChannelHandler("the");
		assertTrue(handlert!=null);
		
		assertTrue(handler1.getPrevHandler() instanceof EmptyChannelHandler);
		assertTrue(handler1.getNextHandler().equals(handlert));
		assertTrue(handlert.getPrevHandler().equals(handler1));
		assertTrue(handlert.getNextHandler().equals(handler2));
		assertTrue(handler2.getPrevHandler().equals(handlert));
		assertTrue(handler2.getNextHandler() instanceof EmptyChannelHandler);
	}

	public void testAddAfter() {
		dataChain.addFirst("one", oneHandler);
		dataChain.addLast("two", twoHandler);
		dataChain.addAfter("two", "the", theHandler);
		
		ChannelHandler handler1 = dataChain.getChannelHandler("one");
		assertTrue(handler1!=null);
		
		ChannelHandler handler2 = dataChain.getChannelHandler("two");
		assertTrue(handler2!=null);
		
		ChannelHandler handlert = dataChain.getChannelHandler("the");
		assertTrue(handlert!=null);
		
		assertTrue(handler1.getPrevHandler() instanceof EmptyChannelHandler);
		assertTrue(handler1.getNextHandler().equals(handler2));
		assertTrue(handler2.getPrevHandler().equals(handler1));
		assertTrue(handler2.getNextHandler().equals(handlert));
		assertTrue(handlert.getPrevHandler().equals(handler2));
		assertTrue(handlert.getNextHandler() instanceof EmptyChannelHandler);
	}
	
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
