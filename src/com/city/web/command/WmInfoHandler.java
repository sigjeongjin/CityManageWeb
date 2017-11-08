package com.city.web.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.Address;
import com.city.model.LocationManagement;
import com.city.model.SensorInfo;
import com.city.web.service.AddressService;
import com.city.web.service.ManageLocationService;
import com.city.web.service.SensorManageService;

public class WmInfoHandler implements CommandHandler {

	private ManageLocationService manageLocationService = new ManageLocationService();
	private SensorManageService sensorManageService = new SensorManageService();
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

	private String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.processSubmit(request, response);
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String manageId = request.getParameter(MANAGE_ID);
		
		LocationManagement manageInfo = manageLocationService.getManagementInfo(manageId);
		
		List<SensorInfo> sensorInfo = new ArrayList<>();
		List<Address> addressCityList = new ArrayList<>();
		
		sensorInfo = sensorManageService.getSensorInfoList(manageId);
		
		addressCityList = addressService.getAddressCity();
		
		request.getSession().setAttribute("sensorInfoList", sensorInfo);
		request.getSession().setAttribute("manageInfo", manageInfo);
		request.setAttribute("addressCityList", addressCityList);

		return "/view/management/manageInfoForm.jsp";
	}
}