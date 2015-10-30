package com.team.task;

import java.util.Map;
import java.util.concurrent.Callable;

import net.sf.json.JSONObject;

import com.team.service.VowelFrequencyService;
import com.team.util.CacheHelper;

public class VowelCounterTask implements Callable<Boolean> {

	String reqId;
	private VowelFrequencyService vowelFrequencyService;
	private String line;

	public VowelCounterTask(String reqId,
			VowelFrequencyService vowelFrequencyService, String line) {
		this.reqId = reqId;
		this.vowelFrequencyService = vowelFrequencyService;
		this.line = line;
	}

	@Override
	public Boolean call() throws Exception {

		// Vowel Counter
		try {
			Map<String, Object> vowelResult = 	vowelFrequencyService.getVowelFrequency(line);

			JSONObject jsonObject = JSONObject.fromObject(vowelResult);
			
			if (CacheHelper.getCacheData(reqId) != null) {
				Map<String, Object> ridMap = CacheHelper.getCacheData(reqId);

				ridMap.put("vowel_freq", jsonObject.toString());
				ridMap.put("completed_tasks", "2/5");
				
				CacheHelper.setCacheData(reqId, ridMap);
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
