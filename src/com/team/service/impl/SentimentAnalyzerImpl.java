package com.team.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.team.service.SentimentAnalyzerService;
import com.team.service.WordFrequencyService;
import com.team.util.Config;

public class SentimentAnalyzerImpl implements SentimentAnalyzerService {

	WordFrequencyService wordFrequecy;

	public WordFrequencyService getWordFrequecy() {
		return wordFrequecy;
	}

	public void setWordFrequecy(WordFrequencyService wordFrequecy) {
		this.wordFrequecy = wordFrequecy;
	}

	@Override
	public Map<String, Object> getSentimentStatus(String line) {
		Map<String, Object> resultMap=new HashMap<>();
		
		Map<String, Integer> wordCount = wordFrequecy.getFrequency(line);
		JSONObject jsonObject = JSONObject.fromObject(wordCount);
		
		resultMap.put("word_freq", jsonObject);
		int score = 0;
		String sentimentStatus = Config.NEUTRAL;

		for (String word : wordCount.keySet()) {
			int status = getWordStatus(word);
			score += wordCount.get(word) * status;
		}

		if (score > 2) {
			sentimentStatus = Config.POSITIVE;
		} else if (score < -2) {
			sentimentStatus = Config.NEGATIVE;
		} else
			sentimentStatus = Config.NEUTRAL;
		
		resultMap.put("sentiment", sentimentStatus);
		
		return resultMap;
		
	}

	public int getWordStatus(String word) {
		if (Config.POSITIVE_WORDS.contains(word)) {
			return 1;
		} else if (Config.NEGATIVE_WORDS.contains(word)) {
			return -1;
		} else {
			return 0;
		}
	}
}
