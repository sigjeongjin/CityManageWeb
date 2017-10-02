package com.city.ajax.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.city.model.CityAjaxJSON;
import com.city.web.service.AddressService;

public class AddressCityHandler implements CommandJsonHandler {

	private AddressService addressService = new AddressService();

	public JSONObject process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		} else if (request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private JSONObject processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.processSubmit(request, response);
	}

	private JSONObject processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<CityAjaxJSON> addressCityList = new ArrayList<>();
		addressCityList = addressService.getCityList();

		JSONArray jr = new JSONArray();
		for (int i = 0; i < addressCityList.size(); i++) {
			JSONObject object = new JSONObject();

			object.put("stateCode", addressCityList.get(i).getCityCode());
			object.put("stateName", addressCityList.get(i).getCityName());
			jr.add(object);
		}

		JSONObject objectCity = new JSONObject();
		objectCity.put("cityList", jr);

		return objectCity;

	}
}
