package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.Member;
import com.city.web.service.MemberManageService;

public class MemberInfoHandler implements CommandHandler {
	
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
		return "/view/member/memberListView.jsp";
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		String memberId = request.getParameter("memberId");
		System.out.println("memberId" + memberId);

		Member member = memberManageService.memberSelect(memberId);
		request.getSession().setAttribute("memberInfo", member);
		
		return "/view/member/memberInfoForm.jsp";	
	}
}

