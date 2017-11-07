package com.city.api.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.SensorService;
import com.city.model.SensorResultInfo;
import com.city.model.SensorResultListJSON;

public class SensorMapInfoListHandler  implements CommandJsonHandler {

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

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		SensorResultListJSON sensorResultListJSON = new SensorResultListJSON();
		
		String memberId = req.getParameter(MEMBER_ID);
		String manageType = req.getParameter(MANAGE_TYPE);

		List<SensorResultInfo> sensorResultInfo = sensorService.getSensorMapInfoList(memberId, manageType);
		
		if(sensorResultInfo != null) {
			sensorResultListJSON.setResultCode(RESULT_SUCCESS);
			sensorResultListJSON.setResultMessage(SEARCH_SUCCESS_MESSAGE);
			sensorResultListJSON.setSensorList(sensorResultInfo);
		} else {
			sensorResultListJSON.setResultCode(RESULT_FAIL);
			sensorResultListJSON.setResultMessage(SEARCH_FAIL_MESSAGE);
		}
		return gson.toJson(sensorResultInfo);
	}
}