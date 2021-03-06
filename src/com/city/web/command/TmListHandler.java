package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.web.service.SensorListPage;
import com.city.web.service.SensorManageService;

public class TmListHandler implements CommandHandler {

	private SensorManageService sensorManageService = new SensorManageService();

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String manageType = "tm";
		String pageNoVal = request.getParameter("pageNo");
		int pageNo = 1;
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}

		SensorListPage sensorListPage = sensorManageService.getSensorListPage(pageNo, manageType);
		request.setAttribute("tmSensorListPage", sensorListPage);

		request.getSession().setAttribute("manageType", manageType);

		return "/view/management/tmListView.jsp";
	}
}