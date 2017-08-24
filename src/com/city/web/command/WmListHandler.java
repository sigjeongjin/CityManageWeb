package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.web.service.SensorListPage;
import com.city.web.service.SensorManageService;
import com.google.gson.Gson;


public class WmListHandler implements CommandHandler {
	
	private SensorManageService sensorManageService = new SensorManageService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String manageType = "wm";
		String pageNoVal = request.getParameter("pageNo");
		int pageNo = 1;
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		
		System.out.println("되녀?");
		SensorListPage sensorListPage = sensorManageService.getSensorListPage(pageNo, manageType);
		request.setAttribute("sensorListPage", sensorListPage);	
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(sensorListPage));
		System.out.println("되냐?");
		request.getSession().setAttribute("manageType", manageType);
		return "/view/management/wmListView.jsp";
	}
}