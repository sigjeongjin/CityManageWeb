package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.SensorRegisterService;
import com.city.model.Result;
import com.google.gson.Gson;

public class OperationStatusRegisterHandler implements CommandJsonHandler {

	private SensorRegisterService sensorRegisterService = new SensorRegisterService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	private String processForm(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		return this.processSubmit(req, res);
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		String sensorId = req.getParameter("sensorId");
		String operationStatus = req.getParameter("operationStatus");
		String resultCode = sensorRegisterService.operationStatus(sensorId, operationStatus);
		Result result = new Result();
		
		
		result.setResultCode("200");
		result.setResultMessage("저장되었습니다.");
		
		
		Gson gson = new Gson();
		return gson.toJson(result);

	}

}