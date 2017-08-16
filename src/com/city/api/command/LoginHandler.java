package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.LoginService;
import com.google.gson.Gson;

public class LoginHandler implements CommandJsonHandler {

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

	private String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

		return this.processSubmit(request, response);
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		String memberId = trim(request.getParameter("memberId"));
		String memberPwd = trim(request.getParameter("memberPwd"));
		
		System.out.println("memberId : " + memberId);
		System.out.println("memberPwd : " + memberPwd);
		String resultCode = loginService.login(memberId, memberPwd);
		System.out.println("resultCode : " + resultCode);
		Gson gson = new Gson();
		
		return gson.toJson(resultCode);
	}
	
	private String trim(String str) {
		return str == null ? null : str.trim();
	}

}
