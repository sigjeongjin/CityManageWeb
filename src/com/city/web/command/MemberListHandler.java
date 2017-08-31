package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.web.service.MemberListPage;
import com.city.web.service.MemberManageService;


public class MemberListHandler implements CommandHandler{

	private MemberManageService memberManageService = new MemberManageService();

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNoVal = request.getParameter("pageNo");
		String memberSelect = request.getParameter("memberSelect");
		String memberInput = request.getParameter("memberInput");
		
		int pageNo = 1;
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		
		String cityCode = request.getSession().getAttribute("cityCode").toString();
		
		MemberListPage memberListPage = memberManageService.getMemberListPage(pageNo,memberSelect,memberInput,cityCode);
		request.setAttribute("memberListPage", memberListPage);
		

		return "/view/member/memberListView.jsp";
	}
}

