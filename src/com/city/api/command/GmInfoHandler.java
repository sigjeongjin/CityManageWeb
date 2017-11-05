package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.SensorService;
import com.city.model.GmResultInfo;

public class GmInfoHandler implements CommandJsonHandler {
	
	private SensorService sensorService = new SensorService();

	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return this.processSubmit(req, res);
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {

		String manageId = req.getParameter(MANAGE_ID);
		
		GmResultInfo gmResultInfo = new GmResultInfo(); 
				
		gmResultInfo = sensorService.getGmInfo(manageId, gmResultInfo);
		
		if(gmResultInfo != null) {
			gmResultInfo.setResultCode(RESULT_SUCCESS);
			gmResultInfo.setResultMessage(SEARCH_SUCCESS_MESSAGE);
		} else {
			gmResultInfo.setResultCode(RESULT_FAIL);
			gmResultInfo.setResultMessage(SEARCH_FAIL_MESSAGE);
		}
		
		return gson.toJson(gmResultInfo);
	}
}