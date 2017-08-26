package com.city.web.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.Address;
import com.city.model.LocationManagement;
import com.city.model.Member;
import com.city.web.service.AddressService;
import com.city.web.service.ManageLocationService;

public class WmInfoHandler implements CommandHandler {
	
	private ManageLocationService manageLocationService = new ManageLocationService();
	private AddressService addressService = new AddressService();
	
	@Override
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
	
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return "/view/management/manageInfoForm.jsp";
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		String manageId = request.getParameter("manageId");
		
		String cityName = request.getParameter("cityName");
		String stateName = request.getParameter("stateName");
		request.setAttribute("cityName", cityName);
		request.setAttribute("stateName", stateName);

		LocationManagement locationManagement = manageLocationService.manageLocationSelect(manageId);
		request.getSession().setAttribute("wmManageInfo", locationManagement);
		
		String sensorTypes = locationManagement.getSensorTypes();
		
		System.out.println("sensorTypes : " + sensorTypes);
		
		List<Address> addressCityList = new ArrayList<>();
		addressCityList = addressService.addressCity();
		request.setAttribute("addressCityList", addressCityList);
		
		return "/view/management/manageInfoForm.jsp";	
	}

}