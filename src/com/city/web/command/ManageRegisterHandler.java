package com.city.web.command;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.LocationManagement;
import com.city.web.service.GmSensorListPage;
import com.city.web.service.ManageLocationService;
import com.city.web.service.SensorManageService;
import com.city.web.service.SmSensorListPage;
import com.city.web.service.TmSensorListPage;
import com.city.web.service.WmSensorListPage;

public class ManageRegisterHandler implements CommandHandler {

	private ManageLocationService manageLocationService = new ManageLocationService();
	private SensorManageService sensorManageService = new SensorManageService();

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

		return "/index.jsp";
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {

		LocationManagement locationManagement = new LocationManagement();

		String manageType = (String) request.getSession().getAttribute("manageType");

		String sensorTypes = Arrays.toString(request.getParameterValues("sensorTypes"));
		sensorTypes = sensorTypes.substring(1, sensorTypes.length() - 1);

		locationManagement.setManageId(request.getParameter("manageId"));
		locationManagement.setLatitude(Double.parseDouble(request.getParameter("latitude")));
		locationManagement.setLongitude(Double.parseDouble(request.getParameter("longitude")));
		locationManagement.setManageType(manageType);
		locationManagement.setSensorTypes(sensorTypes);
		locationManagement.setMemo(request.getParameter("memo"));
		locationManagement.setCityCode(request.getParameter("cityCode"));
		locationManagement.setStateCode(request.getParameter("stateCode"));

		// 관리지역의 센서 정보를 저장 한다.
		manageLocationService.setManagementInfo(locationManagement);

		if (manageType.equals(TM)) {
			TmSensorListPage tmSensorListPage = sensorManageService.getTmSensorListPage(1, manageType, "all", "");
			request.setAttribute("TmListPage", tmSensorListPage);
		} else if (manageType.equals(WM)) {
			WmSensorListPage wmSensorListPage = sensorManageService.getWmSensorListPage(1, manageType, "all", "");
			request.setAttribute("WmListPage", wmSensorListPage);
		} else if (manageType.equals(GM)) {
			GmSensorListPage gmSensorListPage = sensorManageService.getGmSensorListPage(1, manageType, "all", "");
			request.setAttribute("GmListPage", gmSensorListPage);
		} else if (manageType.equals(SM)) {
			SmSensorListPage smSensorListPage = sensorManageService.getSmSensorListPage(1, manageType, "all", "");
			request.setAttribute("SmListPage", smSensorListPage);
		}

		request.getSession().setAttribute("manageType", manageType);
		return "/" + manageType + "List.do";
	}
}