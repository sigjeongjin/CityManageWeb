package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.Result;
import com.google.gson.Gson;

public interface CommandJsonHandler {
	public Gson gson = new Gson();
	
	public Result result = new Result();

	public final String RESULT_SUCCESS = "200";
	public final String RESULT_SUCCESS_MESSAGE = "정보가 조회 되었습니다.";
	
	public final String RESULT_FAIL = "204";
	public final String RESULT_FAIL_MESSAGE = "조회된 정보가 없습니다.";
	
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception;
}