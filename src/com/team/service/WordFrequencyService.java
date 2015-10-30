package com.team.service;

import java.util.Map;

public interface WordFrequencyService {

	public Map<String, Integer> getFrequency(String line);
}
