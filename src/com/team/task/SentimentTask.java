package com.team.task;

import java.util.Map;
import java.util.concurrent.Callable;

import com.team.service.SentimentAnalyzerService;
import com.team.util.CacheHelper;

public class SentimentTask implements Callable<Boolean> {

	String reqId;
	private SentimentAnalyzerService sentimentAnalyzerService;;
	private String line;

	public SentimentTask(String reqId,SentimentAnalyzerService sentimentAnalyzerService, String line) {
		this.reqId=reqId;
		this.sentimentAnalyzerService=sentimentAnalyzerService;
		this.line = line;
	}

	@Override
	public Boolean call() throws Exception {
		
		//Sentinzation
		try {
			Map<String, Object> senitizerResult=sentimentAnalyzerService.getSentimentStatus(line);
			
			if(CacheHelper.getCacheData(reqId)!=null){
				Map<String, Object> ridMap=CacheHelper.getCacheData(reqId);
				
				for (String key : senitizerResult.keySet()) {
					ridMap.put(key, senitizerResult.get(key));
				}
				
				ridMap.put("completed_tasks", "3/5");
				CacheHelper.setCacheData(reqId, ridMap);
			}
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}


}
