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

	private JSONObject processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String cityCode = request.getParameter("cityCode");

		System.out.println("cityCode : " + cityCode);

		List<Address> addressStateList = new ArrayList<>();
		addressStateList = addressService.addressState(cityCode);
		// request.setAttribute("addressStateList", addressStateList);

		JSONArray jr = new JSONArray();
		for (int i = 0; i < addressStateList.size(); i++) {
			JSONObject object = new JSONObject();

			object.put("stateCode", addressStateList.get(i).getStateCode());
			object.put("stateName", addressStateList.get(i).getStateName());
			jr.add(object);
		}

		JSONObject objectState = new JSONObject();
		objectState.put("state", jr);

		return objectState;

	}
}
