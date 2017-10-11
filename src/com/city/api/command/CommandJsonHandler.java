package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandJsonHandler {
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception;
}


