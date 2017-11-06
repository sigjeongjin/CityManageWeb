package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.SensorInfo;
import com.city.web.service.GmSensorListPage;
import com.city.web.service.SensorManageService;
import com.city.web.service.SmSensorListPage;
import com.city.web.service.TmSensorListPage;
import com.city.web.service.WmSensorListPage;

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

		String manageType = request.getSession().getAttribute("manageType").toString();

		SensorInfo sensorInfo = new SensorInfo();
		sensorInfo.setManageId(request.getParameter("manageId"));
		sensorInfo.setSensorId(request.getParameter("sensorId"));
		sensorInfo.setSensorType(request.getParameter("sensorType"));
		sensorInfo.setOperationStatus("Y");
		sensorInfo.setSensorNoticeStandard(request.getParameter("sensorNoticeStandard"));

		sensorManageService.setSensorInfo(sensorInfo);

		if(manageType.equals(TM)) {
			TmSensorListPage tmSensorListPage = sensorManageService.getTmSensorListPage(1, manageType,  "all", "");
			request.setAttribute("TmListPage", tmSensorListPage);
		} else if(manageType.equals(WM)) {
			WmSensorListPage wmSensorListPage = sensorManageService.getWmSensorListPage(1, manageType, "all", "");
			request.setAttribute("WmListPage", wmSensorListPage);
		} else if(manageType.equals(GM)) {
			GmSensorListPage gmSensorListPage = sensorManageService.getGmSensorListPage(1, manageType, "all", "");
			request.setAttribute("GmListPage", gmSensorListPage);
		} else if(manageType.equals(SM)) {
			SmSensorListPage smSensorListPage = sensorManageService.getSmSensorListPage(1, manageType, "all", "");
			request.setAttribute("SmListPage", smSensorListPage);
		}
		
		request.getSession().setAttribute("manageType", manageType);
		return "/" + manageType + "List.do";
	}
}
