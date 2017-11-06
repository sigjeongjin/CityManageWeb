package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.web.service.SensorManageService;
import com.city.web.service.TmSensorListPage;

public class TmListHandler implements CommandHandler {

	private SensorManageService sensorManageService = new SensorManageService();

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String selectBox = request.getParameter("selectBox");
		String searchText = request.getParameter("searchText");
		String pageNoVal = request.getParameter("pageNo");
			
		int pageNo = 1;
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}

		TmSensorListPage tmSensorListPage = sensorManageService.getTmSensorListPage(pageNo, TM,  selectBox, searchText);
		request.setAttribute("TmListPage", tmSensorListPage);

		request.getSession().setAttribute("manageType", TM);

		return "/view/management/tmListView.jsp";
	}
}