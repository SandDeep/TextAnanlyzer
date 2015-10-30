package com.team.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.team.service.VowelFrequencyService;

public class VowelFreqImpl implements VowelFrequencyService {

	@Override
	public Map<String, Object> getVowelFrequency(String line) {

		line.toLowerCase();
		Map<String, Object> vowelMap = new HashMap<>();

		for (int i = 0; i < line.length(); ++i) {
			switch (line.charAt(i)) {
			case 'a':
				updateMap(vowelMap, "a");
				break;
			case 'e':
				updateMap(vowelMap, "e");
				break;
			case 'i':
				updateMap(vowelMap, "i");
				break;
			case 'o':
				updateMap(vowelMap, "o");
				break;
			case 'u':
				updateMap(vowelMap, "u");
				break;
			default:
				// do nothing
			}
		}

		return vowelMap;
	}

	private void updateMap(Map<String, Object> map, String string) {
		if (map.get(string) == null) {
			map.put(string, 1);
		} else {
			int count = (int)map.get(string) + 1;
			map.put(string, count);
		}
	}
}
