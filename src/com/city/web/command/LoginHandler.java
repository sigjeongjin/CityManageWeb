package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.LoginService;


public class LoginHandler implements CommandHandler {
	

	private LoginService loginService = new LoginService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) 
	throws Exception {
		if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) 
	throws Exception {
		
			return null;
		}
	private String trim(String str) {
		return str == null ? null : str.trim();
	}
}
