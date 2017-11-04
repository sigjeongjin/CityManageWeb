package com.city.api.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.SensorService;
import com.city.model.SensorResultInfo;
import com.city.model.SensorResultListJSON;

public class SensorListHandler implements CommandJsonHandler {
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

		SensorResultListJSON sensorResultListJSON = new SensorResultListJSON();
		
		String memberId = req.getParameter("memberId");
		String manageType = req.getParameter("manageType");

		List<SensorResultInfo> sensorResultInfoList = sensorService.getSensorList(memberId, manageType);
		
		sensorResultListJSON.setSensorList(sensorResultInfoList);

		return gson.toJson(sensorResultListJSON);
	}
}
