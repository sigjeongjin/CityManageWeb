package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.Member;
import com.city.web.service.LoginService;

public class LoginHandler implements CommandHandler {

	private LoginService loginService = new LoginService();

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
		return "view/loginFrom.jsp";
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String memberId = trim(request.getParameter("memberId"));
		String memberPwd = trim(request.getParameter("memberPwd"));

		try {
			Member member = loginService.login(memberId, memberPwd);
			request.getSession().setAttribute("authMember", member);
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return null;
		} catch (RuntimeException e) {
			return "view/loginFrom.jsp";
		}
	}

	private String trim(String str) {
		return str == null ? null : str.trim();
	}
}
