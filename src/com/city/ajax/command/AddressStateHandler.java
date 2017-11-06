package com.city.ajax.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.city.model.Address;
import com.city.web.service.AddressService;

public class AddressStateHandler implements CommandJsonHandler {

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

	@SuppressWarnings("unchecked")
	private JSONObject processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Address> addressStateList = new ArrayList<>();
		JSONArray stateJsonArray = new JSONArray();
		
		String cityCode = request.getParameter(CITY_CODE);

		addressStateList = addressService.getAddressState(cityCode);
		
		for (int i = 0; i < addressStateList.size(); i++) {
			JSONObject stateJsonObj = new JSONObject();

			stateJsonObj.put("stateCode", addressStateList.get(i).getStateCode());
			stateJsonObj.put("stateName", addressStateList.get(i).getStateName());
			stateJsonArray.add(stateJsonObj);
		}

		JSONObject stateJsonObj = new JSONObject();
		stateJsonObj.put("state", stateJsonArray);

		return stateJsonObj;

	}
}
