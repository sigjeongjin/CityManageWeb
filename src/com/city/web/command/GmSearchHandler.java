package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.web.service.SensorManageService;
import com.city.web.service.GmSensorListPage;

public class GmSearchHandler implements CommandHandler {

	private SensorManageService sensorManageService = new SensorManageService();

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String selectBox = (request.getParameter("selectBox"));
		String searchText = (request.getParameter("searchText"));
		
		String manageType = "gm";
		String pageNoVal = request.getParameter("pageNo");
		int pageNo = 1;
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}

		GmSensorListPage gmSensorListPage = sensorManageService.getGmSensorListPage(pageNo, manageType,  selectBox, searchText);
		request.setAttribute("GmListPage", gmSensorListPage);

		request.getSession().setAttribute("manageType", manageType);

		return "/view/management/gmListView.jsp";
	}
}