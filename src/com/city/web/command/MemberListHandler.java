package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.web.service.MemberListPage;
import com.city.web.service.MemberManageService;


public class MemberListHandler implements CommandHandler{

	private MemberManageService memberManageService = new MemberManageService();

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String selectBox = request.getParameter("selectBox");
		String searchText = request.getParameter("searchText");
		String pageNoVal = request.getParameter("pageNo");
		
		int pageNo = 1;
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		
		String cityCode = request.getSession().getAttribute("cityCode").toString();
		
		MemberListPage memberListPage = memberManageService.getMemberListPage(pageNo, selectBox, searchText, cityCode);
		request.setAttribute("memberListPage", memberListPage);
		
		return "/view/member/memberListView.jsp";
	}
}