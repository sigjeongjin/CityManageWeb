package com.city.api.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.city.api.service.SensorService;
import com.city.model.SensorInfo;
import com.city.model.SensorMapInfoList;

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

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<SensorInfo> sensorMapInfoList = new ArrayList<SensorInfo>();
		
		SensorMapInfoList sensorMapInfoListObj = new SensorMapInfoList();
		
		String memberId = request.getParameter(MEMBER_ID);
		String manageId = request.getParameter(MANAGE_ID);

		sensorMapInfoList = sensorService.getSensorMapInfoList(manageId, memberId);
		
		sensorMapInfoListObj.setSensorMapInfoList(sensorMapInfoList);
		
		if(sensorMapInfoList != null) {
			sensorMapInfoListObj.setResultCode(RESULT_SUCCESS);
			sensorMapInfoListObj.setResultMessage(SEARCH_SUCCESS_MESSAGE);
		} else {
			sensorMapInfoListObj.setResultCode(RESULT_FAIL);
			sensorMapInfoListObj.setResultMessage(SEARCH_FAIL_MESSAGE);
		}
		return gson.toJson(sensorMapInfoList);
	}
}