package com.team.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.team.service.SanitizerService;
import com.team.util.Config;

public class SanitizeImpl implements SanitizerService {

	private Log log = LogFactory.getLog(SanitizeImpl.class);

	@Override
	public boolean isBlacklisted(String word) {
		log.debug("Checking Blacklisting : " + word);
		return Config.BLACLIST_WORDS.contains(word);
	}

}
