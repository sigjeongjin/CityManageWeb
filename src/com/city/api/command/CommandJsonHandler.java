package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public interface CommandJsonHandler {
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception;
}


