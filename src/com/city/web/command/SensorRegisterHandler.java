package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.SensorInfo;
import com.city.web.service.SensorManageService;
import com.city.web.service.TmSensorListPage;

public class SensorRegisterHandler implements CommandHandler {

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

		// String sensorType = (String)
		// request.getSession().getAttribute("manageType");

		SensorInfo sensorInfo = new SensorInfo();
		sensorInfo.setManageId(request.getParameter("manageId"));
		sensorInfo.setSensorId(request.getParameter("sensorId"));
		sensorInfo.setSensorType(request.getParameter("sensorType"));
		sensorInfo.setOperationStatus("Y");
		sensorInfo.setSensorNoticeStandard(request.getParameter("sensorNoticeStandard"));

		sensorManageService.sensorRegister(sensorInfo);

		//리스트 화면으로 돌아갈때 데이터를 들고 가기 위해 리스트 조회 하는 서비스 실행
		//기본값으로 셋팅
		TmSensorListPage tmSensorListPage = sensorManageService.getTmSensorListPage(1, TM,  "all", "");
		request.setAttribute("TmListPage", tmSensorListPage);
		request.getSession().setAttribute("manageType", TM);
		
		return "/tmList.do";
	}
}
