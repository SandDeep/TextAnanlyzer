package com.team.util;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class Config {

	public static final String[] positiveWords = { "Happy", "like", "amazing",
			"good", "love", "agree", "wonderful", "true", "enjoyable", "great",
			"incredible" };
	public static final List<String> POSITIVE_WORDS = new ArrayList<>(
			Arrays.asList(positiveWords));

	public static final String[] negativeWords = { "Cry", "hate", "fuck",
			"kill", "die", "disgusting", "awful", "terrible", "horrible" };
	public static final List<String> NEGATIVE_WORDS = new ArrayList<>(
			Arrays.asList(negativeWords));

	public static final String[] blacklistedWords = { "felching", "labia",
			"lmao", "spunk", "tosser", "twat", "nigger", "queer", "balls" };
	public static final List<String> BLACLIST_WORDS = new ArrayList<>(
			Arrays.asList(blacklistedWords));

	// Sentiment Status
	public static final String POSITIVE = "positive";
	public static final String NEGATIVE = "negative";
	public static final String NEUTRAL = "neutral";

	//Status
	public static final String IN_PROGRESS = "in progress";
	public static final String COMPLETE = "completed";
	public static final String FAILED = "failed";
	public static final String ERROR = "error";
	
	public static boolean chkNull(String text) {
		if (text != null && !text.trim().equals("")
				&& !text.equals("undefined")) {
			return false;
		}
		return true;
	}

	public static void writeJSON(HttpServletResponse res, Map<String, ?> map) throws IOException {
		res.setContentType("application/x-javascript");

		JSONObject jsonObject = JSONObject.fromObject(map);
		StringBuffer sb = new StringBuffer();
		if (jsonObject.toString() != null
				&& !jsonObject.toString().equals("null")) {
			sb.append(jsonObject.toString());
		} else {
			sb.append("{\"error\" : \"No data found\"}");
		}
		OutputStream os = res.getOutputStream();
		os.write(sb.toString().getBytes());
		os.close();
	}
	
	public static String formatDate(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("MMMM dd HH:mm:ss zzzz yyyy",Locale.ENGLISH);
		return sdf.format(date);
		
	}
}
