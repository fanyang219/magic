package com.sky.magic;

import junit.framework.TestCase;

public class UnitTest extends TestCase {
	private static final String TAG = UnitTest.class.getSimpleName();
	
	public void setUp() throws Exception {
		super.setUp();
	}
	
	public void testMagic() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
