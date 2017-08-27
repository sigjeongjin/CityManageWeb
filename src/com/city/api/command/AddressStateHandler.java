package com.city.api.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.AddressCityService;
import com.city.model.Result;
import com.city.model.State;
import com.city.model.StateJSON;
import com.google.gson.Gson;

public class AddressStateHandler implements CommandJsonHandler {
	
	private AddressCityService addressCityService = new AddressCityService();

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

		String cityCode = req.getParameter("cityCode");
		List<State> state = addressCityService.getStateList(cityCode);
		StateJSON stateJson = new StateJSON();
		stateJson.setResultCode("200");
		stateJson.setResultMessage("조회되었습니다.");
		stateJson.setState(state);
		Gson gson = new Gson();
		return gson.toJson(stateJson);

	}
}
