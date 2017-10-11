package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.SensorRegisterService;
import com.city.model.Result;
import com.google.gson.Gson;

public class OperationStatusRegisterHandler implements CommandJsonHandler {

	private SensorRegisterService sensorRegisterService = new SensorRegisterService();

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

	private String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.processSubmit(request, response);
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sensorId = request.getParameter("sensorId");
		String operationStatus = request.getParameter("operationStatus");
		String resultCode = sensorRegisterService.operationStatus(sensorId, operationStatus);
		Result result = new Result();

		result.setResultCode("200");
		result.setResultMessage("저장되었습니다.");

		Gson gson = new Gson();
		return gson.toJson(result);

	}

}