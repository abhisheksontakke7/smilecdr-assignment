package com.playground.basic.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class PropertiesCache {

	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(PropertiesCache.class);
	private final Properties properties = new Properties();
	
	private PropertiesCache(){
		InputStream ins = this.getClass().getClassLoader().getResourceAsStream("application.properties");
		try {
			properties.load(ins);
		} catch (IOException e) {
			LOGGER.error("Exception occured during property loading. Cause={}", e.getMessage());
		}
	}
	
	private static class LazyHolder
	   {
	      private static final PropertiesCache INSTANCE = new PropertiesCache();
	   }
	 
	   public static PropertiesCache getInstance()
	   {
	      return LazyHolder.INSTANCE;
	   }
	    
	   public String getProperty(String key){
	      return properties.getProperty(key);
	   }
	    
	   public Set<String> getAllPropertyNames(){
	      return properties.stringPropertyNames();
	   }
	    
	   public boolean containsKey(String key){
	      return properties.containsKey(key);
	   }
}
