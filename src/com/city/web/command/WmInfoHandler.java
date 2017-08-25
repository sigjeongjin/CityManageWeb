package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.LocationManagement;
import com.city.model.Member;
import com.city.web.service.ManageLocationService;

public class WmInfoHandler implements CommandHandler {
	
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
		return "/view/management/manageInfoForm.jsp";
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		String manageId = request.getParameter("manageId");
		System.out.println("manageId" + manageId);

		LocationManagement locationManagement = manageLocationService.manageLocationSelect(manageId);
		request.getSession().setAttribute("wmManageInfo", locationManagement);
		
		String sensorTypes = locationManagement.getSensorTypes();
		
		System.out.println("sensorTypes : " + sensorTypes);
		
		sensorTypes.toCharArray();
		System.out.println("sensorTypes test: " + sensorTypes.toCharArray());
		
		return "/view/management/manageInfoForm.jsp";	
	}

}