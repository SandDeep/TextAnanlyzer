package com.team.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.team.service.SanitizerService;
import com.team.service.SentimentAnalyzerService;
import com.team.service.TeamManager;
import com.team.service.VowelFrequencyService;
import com.team.task.SentimentTask;
import com.team.task.VowelCounterTask;
import com.team.util.CacheHelper;
import com.team.util.Config;

public class TeamManagerImpl implements TeamManager {

	private Log log = LogFactory.getLog(TeamManagerImpl.class);
	
	VowelFrequencyService vowelFrequencyService;
	SentimentAnalyzerService sentimentAnalyzerService;	
	ThreadPoolTaskExecutor threadPool;
	SanitizerService sanitizerService;
	
	public TeamManagerImpl(SentimentAnalyzerService sentimentAnalyzerService,
			VowelFrequencyService vowelFrequencyService,
			ThreadPoolTaskExecutor threadPool,SanitizerService sanitizerService) {
		this.sentimentAnalyzerService=sentimentAnalyzerService;
		this.vowelFrequencyService=vowelFrequencyService;
		this.threadPool=threadPool;
		this.sanitizerService=sanitizerService;
	}
	
	@Override
	public Map<String, Object> processText(String reqId) {
		
		Map<String, Object> finalMap = null;
		
		try {
			Map<String, Object> line=CacheHelper.getCacheData(reqId);
			line.put("start_time", Config.formatDate(new Date()));
			line.put("status", Config.IN_PROGRESS);
			line.put("completed_tasks", "0/5");
			
			CacheHelper.setCacheData(reqId, line);
			
			boolean isBad=sanitizerService.isBlacklisted((String)line.get(reqId));
			
			if(isBad){
				log.info("Tasks Ignored");
				finalMap=CacheHelper.getCacheData(reqId);
				finalMap.put("end_Time", Config.formatDate(new Date()));
				finalMap.put("status", Config.ERROR);
				finalMap.put("completed_tasks", "1/5");
				
				CacheHelper.setCacheData(reqId, finalMap);
			}
			
			//SentimentAnalyzer
			SentimentTask sanitizerTask=new SentimentTask(reqId, sentimentAnalyzerService, (String)line.get(reqId));

			//Voweler
			VowelCounterTask vowelCounterTask=new VowelCounterTask(reqId, vowelFrequencyService, (String)line.get(reqId));
			
			Collection<Future<?>> futures = new LinkedList<Future<?>>();
			futures.add(threadPool.submit(sanitizerTask));
			futures.add(threadPool.submit(vowelCounterTask));
			
			Boolean s = false;

			for (Future<?> future : futures) {
				s = (Boolean) future.get(/* 1200, TimeUnit.SECONDS */);
				if(!s){
					break;
				}
			}
			
			if(s){
				log.info("Tasks Success");
				finalMap=CacheHelper.getCacheData(reqId);
				finalMap.put("end_Time",Config.formatDate(new Date()));
				finalMap.put("status",Config.COMPLETE);
				finalMap.put("completed_tasks", "5/5");
				
				CacheHelper.setCacheData(reqId, finalMap);
			}
			return finalMap;
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}/* catch (TimeoutException e) {
			e.printStackTrace();
		}*/
		return null;
	}

}
