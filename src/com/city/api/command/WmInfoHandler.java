package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.SensorService;
import com.city.model.WmResultInfo;
import com.google.gson.Gson;

public class WmInfoHandler implements CommandJsonHandler {
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

		String manageId = req.getParameter("manageId");
		WmResultInfo wmResultInfo = sensorService.getWmInfo(manageId);

		Gson gson = new Gson();
		return gson.toJson(wmResultInfo);
	}
}