package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.LocationManagement;
import com.city.web.service.ManagementAreaRegisterService;

public class ManagementAreaRegisterHandler implements CommandHandler {

	private ManagementAreaRegisterService managementAreaRegisterService = new ManagementAreaRegisterService();

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
			
		return "index.jsp";
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {

		LocationManagement locationManagement = new LocationManagement();

		locationManagement.setManageId(request.getParameter("manageId"));
		locationManagement.setLatitude(Double.parseDouble(request.getParameter("latitude")));
		locationManagement.setLongitude(Double.parseDouble(request.getParameter("longitude")));
		locationManagement.setMemo(request.getParameter("memo"));
		locationManagement.setSensorTypes(request.getParameter("sensorTypes"));
		locationManagement.setCityGeocode(request.getParameter("cityGeocode"));
		locationManagement.setStateGeocode(request.getParameter("stateGeocode"));

		managementAreaRegisterService.ManagementAreaRegister(locationManagement);
		return "index.jsp";
	}
}