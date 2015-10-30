package com.team.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CacheHelper {

	private static Log log = LogFactory.getLog(CacheHelper.class);
	
	static HashMap<String, Map<String, Object>> cache = new HashMap<String, Map<String, Object>>();
	
	public static Map<String, Object> getCacheData(String key)
	{
		if(cache.containsKey(key))
		{
			log.debug("Get Cache" + cache.size());
			return cache.get(key);
		}
		log.debug("Cache not found for key-- " + key + "  At time" + new Date());
		log.debug("Get Cache NULL");
		return null;
	}
	
	public static void setCacheData(String key, Map<String, Object> value)
	{
		cache.put(key, value);
		log.debug("Cache Size - " + cache.size());
	}
}
