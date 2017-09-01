package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.SensorInfo;
import com.city.web.service.SensorManageService;

public class SensorRegisterHandler implements CommandHandler {

	private SensorManageService sensorManageService = new SensorManageService();

	@Override
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

	private String processForm(HttpServletRequest request, HttpServletResponse response) {
			
		return this.processSubmit(request, response);
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {

		//String sensorType = (String) request.getSession().getAttribute("manageType");
	
		SensorInfo sensorInfo = new SensorInfo();
		sensorInfo.setManageId(request.getParameter("manageId"));
		sensorInfo.setSensorId(request.getParameter("sensorId"));
		sensorInfo.setSensorType(request.getParameter("sensorType"));
		sensorInfo.setOperationStatus("Y");
		sensorInfo.setSensorNoticeStandard(request.getParameter("sensorNoticeStandard"));

		
		sensorManageService.sensorRegister(sensorInfo);
		
		return "/allList.do";
	}
}

