package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.SensorRegisterService;
import com.city.model.Result;
import com.google.gson.Gson;

public class SensorRegisterHandler implements CommandJsonHandler {

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

		Result result = new Result();
		String sensorInfo = req.getParameter("sensorInfo");
		String sensorId = req.getParameter("sensorId");

		String resultCode = sensorRegisterService.sensorRegister(sensorId,sensorInfo);
				
		if(resultCode.equals("Y")){
			result.setResultCode("200");
			result.setResultMessage("변경되었습니다.");
		} else {
			result.setResultCode("400");
			result.setResultMessage("조회실패");
		}
		
		Gson gson = new Gson();
		return gson.toJson(result);
	}
}