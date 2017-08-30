package com.city.web.command;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.LocationManagement;
import com.city.web.service.ManageLocationService;

public class ManageRegisterHandler implements CommandHandler {

	private ManageLocationService manageLocationService = new ManageLocationService();

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

		String manageType = (String) request.getSession().getAttribute("manageType");
		
		System.out.println("manageType : " + manageType);
		System.out.println("manageId : " + request.getParameter("manageId"));
		
		String sensorTypes = Arrays.toString(request.getParameterValues("sensorTypes"));	
		sensorTypes = sensorTypes.substring(1, sensorTypes.length()-1);

		LocationManagement locationManagement = new LocationManagement();
		locationManagement.setManageId(request.getParameter("manageId"));
		locationManagement.setLatitude(Double.parseDouble(request.getParameter("latitude")));
		locationManagement.setLongitude(Double.parseDouble(request.getParameter("longitude")));
		locationManagement.setManageType(manageType);
		locationManagement.setSensorTypes(sensorTypes);
		locationManagement.setMemo(request.getParameter("memo"));
		//locationManagement.setCityCode(request.getParameter("cityCode"));
		//locationManagement.setStateCode(request.getParameter("stateCode"));
	

		manageLocationService.managementRegister(locationManagement);
		return "index.jsp";
	}
}