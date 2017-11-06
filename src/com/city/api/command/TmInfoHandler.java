package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.city.api.service.SensorService;
import com.city.model.TmResultInfo;

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
		
		String memberId = request.getParameter(MEMBER_ID);
		String manageId = request.getParameter(MANAGE_ID);

		TmResultInfo tmResultInfo = sensorService.getTmInfo(manageId, memberId);
		
		if(StringUtils.isNotEmpty(tmResultInfo.getManageId())) {
			tmResultInfo.setResultCode(RESULT_SUCCESS);
			tmResultInfo.setResultMessage(SEARCH_SUCCESS_MESSAGE);
		} else {
			tmResultInfo.setResultCode(RESULT_FAIL);
			tmResultInfo.setResultMessage(SEARCH_FAIL_MESSAGE);
		}
		return gson.toJson(tmResultInfo);
	}
}