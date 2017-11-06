package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.city.api.service.SensorService;
import com.city.model.WmResultInfo;

public class WmInfoHandler implements CommandJsonHandler {
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
		
		WmResultInfo wmResultInfo = sensorService.getWmInfo(manageId, memberId);

		if(StringUtils.isNotEmpty(wmResultInfo.getManageId())) {
			wmResultInfo.setResultCode(RESULT_SUCCESS);
			wmResultInfo.setResultMessage(SEARCH_SUCCESS_MESSAGE);
		} else {
			wmResultInfo.setResultCode(RESULT_FAIL);
			wmResultInfo.setResultMessage(SEARCH_FAIL_MESSAGE);
		}
		return gson.toJson(wmResultInfo);
	}
}