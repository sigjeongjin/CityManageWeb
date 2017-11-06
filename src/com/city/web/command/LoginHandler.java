package com.city.web.command;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

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

	private String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.processSubmit(request, response);
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, String> idAndName = new HashMap<String, String>();
		
		String memberId = request.getParameter(MEMBER_ID);
		String memberPwd = request.getParameter(MEMBER_PWD);
		
		try {
			idAndName = loginService.login(memberId, memberPwd);
			
			if(StringUtils.isNotEmpty(idAndName.get("error"))) {
				request.setAttribute("error", idAndName.get("error"));
				return "/view/member/loginForm.jsp";
			} else {
				request.getSession().setAttribute("userId", idAndName.get("memberId"));
				request.getSession().setAttribute("userName", idAndName.get("memberName"));
				request.getSession().setAttribute("cityCode", idAndName.get("cityCode"));
						
				return "/memberList.do";
			}		
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			System.out.println(e);
			return "/view/member/loginForm.jsp";
		}
	}

	private String trim(String str) {
		return str == null ? null : str.trim();
	}
}
