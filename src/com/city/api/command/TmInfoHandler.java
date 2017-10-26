package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.SensorService;
import com.city.model.TmResultInfo;
import com.google.gson.Gson;

public class TmInfoHandler implements CommandJsonHandler {

	private SensorService sensorService = new SensorService();

	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		} else if (request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.processSubmit(request, response);
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String manageId = request.getParameter("manageId");
		String memberId = request.getParameter("memberId");
		
		TmResultInfo tmResultInfo = sensorService.getTmInfo(manageId, memberId);
		tmResultInfo.setResultCode("200");
		tmResultInfo.setResultMessage("센서 정보가 조회 되었습니다.");
		
		Gson gson = new Gson();
		return gson.toJson(tmResultInfo);
	}
}