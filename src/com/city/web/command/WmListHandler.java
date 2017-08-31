package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.web.service.SensorManageService;
import com.city.web.service.WmSensorListPage;

public class WmListHandler implements CommandHandler {

	private SensorManageService sensorManageService = new SensorManageService();

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String selectBox = (request.getParameter("selectBox"));
		String searchText = (request.getParameter("searchText"));
			
		String manageType = "wm";
		String pageNoVal = request.getParameter("pageNo");
		int pageNo = 1;
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}

		WmSensorListPage wmSensorListPage = sensorManageService.getWmSensorListPage(pageNo, manageType,  selectBox, searchText);
		request.setAttribute("WmListPage", wmSensorListPage);

		request.getSession().setAttribute("manageType", manageType);

		return "/view/management/wmListView.jsp";
	}
}