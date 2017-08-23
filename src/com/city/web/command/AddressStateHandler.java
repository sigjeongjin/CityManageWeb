package com.city.web.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.Address;
import com.city.web.service.AddressService;

public class AddressStateHandler implements CommandHandler{

	private AddressService addressService = new AddressService();

	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		} else if (request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.processSubmit(request, response);
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String cityCode = request.getParameter("cityCode");
		
		System.out.println("cityCode" + cityCode);
		
		List<Address> addressStateList = new ArrayList<>();
		addressStateList = addressService.addressState(cityCode);
		request.setAttribute("addressStateList", addressStateList);
		return "/view/ManageRegisterForm.jsp";

	}
}
