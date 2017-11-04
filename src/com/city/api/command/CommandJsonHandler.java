package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.Result;
import com.google.gson.Gson;

public interface CommandJsonHandler {
	public Gson gson = new Gson();
	public Result result = new Result();
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception;
}