package com.city.api.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.AddressCityService;
import com.city.model.State;
import com.city.model.StateJSON;

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
		
		String cityCode = req.getParameter(CITY_CODE);
		
		StateJSON stateJson = new StateJSON();
		
		List<State> state = addressCityService.getStateList(cityCode);
		
		if(state != null) {
			stateJson.setResultCode(RESULT_SUCCESS);
			stateJson.setResultMessage(SEARCH_SUCCESS_MESSAGE);
			stateJson.setState(state);	
		} else {
			stateJson.setResultCode(RESULT_FAIL);
			stateJson.setResultMessage(SEARCH_FAIL_MESSAGE);
		}
		return gson.toJson(stateJson);
	}
}
