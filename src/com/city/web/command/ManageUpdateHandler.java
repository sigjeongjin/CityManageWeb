package com.city.web.command;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.LocationManagement;
import com.city.model.Member;
import com.city.web.service.ManageLocationService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class ManageUpdateHandler implements CommandHandler {
	
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
		return "/memberList.do";
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String manageType = (String) request.getSession().getAttribute("manageType");
		
		System.out.println("manageType : " + manageType);
		System.out.println("manageId : " + request.getParameter("manageId"));
		
		String sensorTypes = Arrays.toString(request.getParameterValues("sensorTypes"));
		LocationManagement locationManagement = new LocationManagement();
		locationManagement.setManageId(request.getParameter("manageId"));
		locationManagement.setLatitude(Double.parseDouble(request.getParameter("latitude")));
		locationManagement.setLongitude(Double.parseDouble(request.getParameter("longitude")));
		locationManagement.setManageType(manageType);
		locationManagement.setSensorTypes(sensorTypes);
		locationManagement.setMemo(request.getParameter("memo"));
		locationManagement.setCityCode(request.getParameter("cityCode"));
		locationManagement.setStateCode(request.getParameter("stateCode"));

		manageLocationService.managementAreaUpdate(locationManagement);

		return "/memberList.do";	
	}
}
