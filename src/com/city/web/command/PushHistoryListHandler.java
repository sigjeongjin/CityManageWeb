package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.web.service.PushHistoryListPage;
import com.city.web.service.PushService;

public class PushHistoryListHandler implements CommandHandler {
	private PushService pushService = new PushService();

	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pageNoVal = request.getParameter("pageNo");
		String selectBox = request.getParameter("searchSelect");
		String searchText = request.getParameter("searchText");

		int pageNo = 1;
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}

		PushHistoryListPage pushHistoryListPage = pushService.getPushHistoryListPage(pageNo, selectBox, searchText);
		request.setAttribute("pushHistoryListPage", pushHistoryListPage);

		return "/view/push/pushHistoryListView.jsp";
	}
}
