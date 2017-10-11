package com.city.web.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.city.web.service.ManageLocationService;
import com.city.web.service.SensorManageService;

public class SensorInforHandler implements CommandHandler {

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

	private String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.processSubmit(request, response);
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String manageType = (String) request.getSession().getAttribute("manageType");
		// 쓰레기통관리 ListView에서는 manageType: tm
		// 수질관리 ListView에서는 manageType: wm
		// 도시가스관리 ListView에서는 manageType: gm
		// 금역구역관리 ListView에서는 manageType: sm

		String sensorId = sensorManageService.sensorIdSet(manageType);
		request.setAttribute("sensorId", sensorId);
		// select문을 통해 다음 센서ID값 가져옴
		// manageType은 센서ID 조건을 위해 넣어줌

		String sensorManageId = request.getParameter("sensorManageId");
		request.setAttribute("sensorManageId", sensorManageId);
		// 내가 click한 리스트 목록에 있는 manageId 가져옴

		List<String> sensorTypeList = sensorManageService.sensorTypeSelect(sensorManageId);
		String temp = StringUtils.removeStart(sensorTypeList.toString(), "[");
		temp = StringUtils.removeEnd(temp, "]");
		request.setAttribute("sensorTypeList", temp);
		// 해당 sensorId의 sensorTypes(sensorInfo)

		String manageSensorTypes = manageLocationService.sensorTypesSelect(sensorManageId);
		request.setAttribute("manageSensorTypes", manageSensorTypes);
		// 해당 mamberId의 sensorTypes(locationManagement)

		return "/view/management/sensorRegisterForm.jsp";
	}
}