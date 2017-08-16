package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.dao.Result;
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

	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return this.processSubmit(request, response);
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		String memberId = trim(request.getParameter("memberId"));
		String memberPwd = trim(request.getParameter("memberPwd"));

		try {
			Result result = new Result();
			result.setResultCode(loginService.login(memberId, memberPwd));

			if (result.getResultCode() == "200") {
				result.setResultMessage("success");
			}
			Gson gson = new Gson();
			return gson.toJson(result);
		} catch (Exception e) {
			System.out.println("에러");
		}
		return null;
	}

	private String trim(String str) {
		return str == null ? null : str.trim();
	}
}
