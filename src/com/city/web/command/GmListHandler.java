package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.web.service.GmSensorListPage;
import com.city.web.service.SensorManageService;

public class GmListHandler  implements CommandHandler {

	private SensorManageService sensorManageService = new SensorManageService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String manageType = "gm";
		String pageNoVal = request.getParameter("pageNo");
		int pageNo = 1;
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		
		GmSensorListPage gmSensorListPage = sensorManageService.getGmSensorListPage(pageNo, manageType);
		request.setAttribute("GmListPage", gmSensorListPage);	
		
		request.getSession().setAttribute("manageType", manageType);
		return "/view/management/gmListView.jsp";
	}
}