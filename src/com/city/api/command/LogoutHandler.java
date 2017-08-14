package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutHandler implements CommandJsonHandler {
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) 
	throws Exception {
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		res.sendRedirect(req.getContextPath() + "/index.jsp");
		return null;
	}

}


