package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.city.web.service.SensorManageService;
import com.city.web.service.SmSensorListPage;

public class SmListHandler  implements CommandHandler {
	
	private SensorManageService sensorManageService = new SensorManageService();

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String manageType = "sm";
		String pageNoVal = request.getParameter("pageNo");
		int pageNo = 1;
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}

		SmSensorListPage smSensorListPage = sensorManageService.getSmSensorListPage(pageNo, manageType);
		request.setAttribute("SmListPage", smSensorListPage);

		request.getSession().setAttribute("manageType", manageType);

		return "/view/management/smListView.jsp";
	}
}