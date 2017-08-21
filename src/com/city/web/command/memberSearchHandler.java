package com.city.web.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.web.service.MemberListPage;
import com.city.web.service.MemberManageService;

public class memberSearchHandler implements CommandHandler {

	private MemberManageService memberManageService = new MemberManageService();

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		} else if (request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return "/view/memberListView.jsp";
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String memberSelect = trim(request.getParameter("memberSelect"));
		String memberInput = trim(request.getParameter("memberInput"));
		
		if (memberSelect == "allMember") {
			memberInput = "allMember";
			System.out.println("미쳤냐고: " + memberSelect);
		}
		System.out.println("memberSelect: " + memberSelect);
		System.out.println("memberInput: " + memberInput);
		String pageNoVal = request.getParameter("pageNo");
		int pageNo = 1;
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		if (memberSelect == "allMember") {
			MemberListPage memberListPage = memberManageService.getMemberListPage(pageNo);
			request.setAttribute("memberListPage", memberListPage);		
		} else {
			MemberListPage memberListPage = memberManageService.getMemberListPage(pageNo, memberSelect, memberInput);
			request.setAttribute("memberListPage", memberListPage);
		}

		return "/view/memberListView.jsp";
	}

	private String trim(String str) {
		return str == null ? null : str.trim();
	}
}
