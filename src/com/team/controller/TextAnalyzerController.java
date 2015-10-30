package com.team.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.team.service.RequestIDGenerator;
import com.team.service.TeamManager;
import com.team.util.CacheHelper;
import com.team.util.Config;

@Controller
@RequestMapping("/team.html")
public class TextAnalyzerController {

	private Log log = LogFactory.getLog(TextAnalyzerController.class);

	
	@Autowired
	RequestIDGenerator requestIDGenerator;

	@Autowired
	TeamManager teamManager;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getRequestID(HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("In TextAnalyzer Controller : ");

		String reqId = request.getParameter("id");
		
		try {
			if (Config.chkNull(reqId)) {
				return null;
			}
				
			if (CacheHelper.getCacheData(reqId) != null) {
				Map<String, Object> cacheMap=CacheHelper.getCacheData(reqId);
				if(cacheMap.get("status") != null){
					cacheMap.remove(reqId);
					cacheMap.put("id", reqId);
					Config.writeJSON(response, cacheMap);
				}else{
					Map<String, Object> resMap =teamManager.processText(reqId);
					resMap.remove(reqId);
					resMap.put("id", reqId);
					Config.writeJSON(response, resMap);
				}
				
			}
			
		} catch (IOException e) {
			log.error(reqId + " :  " + e.getMessage());
			return null;
		} catch (Exception e) {
			log.error(reqId + " :  " + e.getMessage());
			return null;
		}
		return null;

	}

	@RequestMapping(method = RequestMethod.GET)
	public String getStatus(HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("In TextAnalyzer Controller : ");

		String line = request.getParameter("text");

		try {
			if (Config.chkNull(line)) {
				return null;
			} else {
				line = line.trim();
			}

			Map<String, Object> map = new HashMap<String, Object>();

			if (CacheHelper.getCacheData(line) != null) {
				Map<String, Object> idMap = CacheHelper.getCacheData(line);
				map.put("id", idMap.get(line));
			} else {
				Long id = requestIDGenerator.generateId(line);
				map.put("id", id);
				
				Map<String, Object> cacheMap=new HashMap<>();
				cacheMap.put(id+"", line);
				CacheHelper.setCacheData(id+"", cacheMap);
			}

			Config.writeJSON(response, map);

		} catch (IOException e) {
			log.error(line + " :  " + e.getMessage());
			return null;
		} catch (Exception e) {
			log.error(line + " :  " + e.getMessage());
			return null;
		}
		return null;

	}

	/*public RequestIDGenerator getIdGenerator() {
		return requestIDGenerator;
	}

	public void setIdGenerator(RequestIDGenerator idGenerator) {
		this.requestIDGenerator = idGenerator;
	}
	
	public TeamManager getTeamManager() {
		return teamManager;
	}

	public void setTeamManager(TeamManager teamManager) {
		this.teamManager = teamManager;
	}*/
	
}
