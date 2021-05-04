package com.playground.basic.util;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PropertiesCacheTest {

	@Test
	public void testGetInstance(){
		assertNotNull(PropertiesCache.getInstance());
		assertNotNull(PropertiesCache.getInstance().getProperty("directoryLocation"));
		assertNotNull(PropertiesCache.getInstance().getAllPropertyNames());
		assertTrue(PropertiesCache.getInstance().containsKey("directoryLocation"));
	}
	
}
