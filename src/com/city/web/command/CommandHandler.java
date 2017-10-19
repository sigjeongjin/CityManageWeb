package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandHandler {
	final String TM = "tm"; 
	final String GM = "gm";
	final String SM = "sm";
	final String WM = "wm";
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception;
}

