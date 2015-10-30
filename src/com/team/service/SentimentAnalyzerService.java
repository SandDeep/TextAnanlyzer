package com.team.service;

import java.util.Map;

public interface SentimentAnalyzerService {

	public Map<String, Object> getSentimentStatus(String line);
}
