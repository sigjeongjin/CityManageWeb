package com.city.api.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.AddressCityService;
import com.city.model.City;
import com.city.model.CityJSON;

public class AddressCityHandler implements CommandJsonHandler {

	private AddressCityService addressCityService = new AddressCityService();

	@Override
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

		List<City> city = new ArrayList<City>();
		CityJSON cityjson = new CityJSON();
		
		city = addressCityService.getAddressCityCode();
		
		if(city != null) {
			cityjson.setResultCode(RESULT_SUCCESS);
			cityjson.setResultMessage(SEARCH_SUCCESS_MESSAGE);
			cityjson.setCity(city);
		} else {
			cityjson.setResultCode(RESULT_FAIL);
			cityjson.setResultMessage(SEARCH_FAIL_MESSAGE);
		}
		return gson.toJson(cityjson);

	}
}
