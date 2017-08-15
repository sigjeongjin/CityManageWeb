package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.Member;
import com.city.web.service.MemberManageService;

public class MemberUpdateHandler implements CommandHandler {
	
	MemberManageService memberManageService = new MemberManageService();

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
		return "view/changeMemberInfoForm.jsp";
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		Member member = (Member) request.getSession().getAttribute("authMember");
	
		System.out.println("bb"+request.getParameter("newPwd"));
		//member.setMemberId(member.getMemberId());
		member.setMemberPwd(request.getParameter("newPwd"));
		member.setMemberName(request.getParameter("newName"));
		member.setMemberPhone(request.getParameter("newPhone"));
		member.setMemberEmail(request.getParameter("newEmail"));
//		member.setMemberPhoto(request.getParameter("newPhoto"));

		
		memberManageService.MemberUpdate(member);
		return "index.jsp";
	}



}
