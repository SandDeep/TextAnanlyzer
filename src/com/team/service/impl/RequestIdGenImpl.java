package com.team.service.impl;

import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.team.service.RequestIDGenerator;
import com.team.util.CacheHelper;

public class RequestIdGenImpl implements RequestIDGenerator {
	private Log log = LogFactory.getLog(CacheHelper.class);

	@Override
	public long generateId(String line) {
		Random random = new Random();
		
		int randomInt = random.nextInt(10000);
		log.info("RequestID Generated : " + randomInt);
		
		return randomInt;
	}
	
}
