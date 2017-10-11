package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public interface CommandHandler {
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception;
}

