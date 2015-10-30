package com.team.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.team.service.WordFrequencyService;

public class WordFreqImpl implements WordFrequencyService {

	@Override
	public Map<String, Integer> getFrequency(String line) {
		String[] arr = line.split("\\s+");

		Map<String, Integer> map = new HashMap<>();

		for (int i = 0; i < arr.length; i++) {
			if (map.get(arr[i]) == null) {
				map.put(arr[i], 1);
			} else {
				map.put(arr[i], map.get(arr[i]) + 1);
			}
		}
		return map;
	}

}
