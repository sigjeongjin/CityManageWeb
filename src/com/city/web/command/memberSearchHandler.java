package com.city.web.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
	
		try {
			memberManageService.search(memberSelect, memberInput);
			return "index.jsp";
		} catch (RuntimeException e) {
			System.out.println("RuntimeException");
			return "view/memberListView.jsp";
		}
	}

	private String trim(String str) {
		return str == null ? null : str.trim();
	}
}