package com.sky.magic;

import junit.framework.TestCase;

import com.sky.magic.handler.ChannelHandler;
import com.sky.magic.handler.DefaultChannelHandler;
import com.sky.magic.handler.EmptyChannelHandler;
import com.sky.magic.handler.chain.ChannelChain;
import com.sky.magic.handler.chain.DefaultChannelChain;
import com.sky.magic.handler.wrapper.ChannelHandlerWrapper;

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
		
		ChannelHandlerWrapper wrapper1 = dataChain.getChannelHandlerWrapper("one");
		ChannelHandlerWrapper wrapper2 = dataChain.getChannelHandlerWrapper("two");
		ChannelHandlerWrapper theAssert = wrapper1.getPrevWrapper();
		assertTrue(theAssert.equals(wrapper2));
	}
	
	public void testAddLast() {
		dataChain.clear();
		
		dataChain.addLast("one", oneHandler);
		dataChain.addLast("two", twoHandler);
		
		ChannelHandler handler1 = dataChain.getChannelHandler("one");
		assertTrue(handler1!=null);
		
		ChannelHandler handler2 = dataChain.getChannelHandler("two");
		assertTrue(handler2!=null);
		
		ChannelHandlerWrapper wrapper1 = dataChain.getChannelHandlerWrapper("one");
		ChannelHandlerWrapper wrapper2 = dataChain.getChannelHandlerWrapper("two");
		ChannelHandlerWrapper theAssert = wrapper1.getNextWrapper();
		assertTrue(theAssert.equals(wrapper2));
	}
	
	public void testAddBefore() {
		dataChain.clear();
		dataChain.addFirst("one", oneHandler);
		dataChain.addFirst("two", twoHandler);
		dataChain.addBefore("two", "the", theHandler);
		
		ChannelHandler handler1 = dataChain.getChannelHandler("one");
		assertTrue(handler1!=null);
		
		ChannelHandler handler2 = dataChain.getChannelHandler("two");
		assertTrue(handler2!=null);
		
		ChannelHandler handlert = dataChain.getChannelHandler("the");
		assertTrue(handlert!=null);
		
		ChannelHandlerWrapper theWrapper = dataChain.getChannelHandlerWrapper("the");
		ChannelHandlerWrapper wrapper2 = dataChain.getChannelHandlerWrapper("two");
		ChannelHandlerWrapper theAssert = theWrapper.getNextWrapper();
		assertTrue(theAssert.equals(wrapper2));
	}
	
	public void testAddBefore1() {
		dataChain.clear();
		dataChain.addFirst("one", oneHandler);
		dataChain.addBefore("one", "the", theHandler);
		
		ChannelHandler handler1 = dataChain.getChannelHandler("one");
		assertTrue(handler1!=null);
		
		ChannelHandler handlert = dataChain.getChannelHandler("the");
		assertTrue(handlert!=null);
		
		ChannelHandlerWrapper theWrapper = dataChain.getChannelHandlerWrapper("the");
		ChannelHandlerWrapper wrapper1 = dataChain.getChannelHandlerWrapper("one");
		ChannelHandlerWrapper assert1 = theWrapper.getNextWrapper();
		assertTrue(assert1.equals(wrapper1));
	}

	public void testAddAfter() {
		dataChain.clear();
		dataChain.addFirst("one", oneHandler);
		dataChain.addLast("two", twoHandler);
		dataChain.addAfter("two", "the", theHandler);
		
		ChannelHandler handler1 = dataChain.getChannelHandler("one");
		assertTrue(handler1!=null);
		
		ChannelHandler handler2 = dataChain.getChannelHandler("two");
		assertTrue(handler2!=null);
		
		ChannelHandler handlert = dataChain.getChannelHandler("the");
		assertTrue(handlert!=null);
		
		ChannelHandlerWrapper twoWrapper = dataChain.getChannelHandlerWrapper("two");
		ChannelHandlerWrapper theWrapper = dataChain.getChannelHandlerWrapper("the");
		ChannelHandlerWrapper theAssert = twoWrapper.getNextWrapper();
		assertTrue(theAssert.equals(theWrapper));
	}
	
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
