package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.Result;
import com.google.gson.Gson;

public interface CommandJsonHandler {
	public Gson gson = new Gson();
	
	public Result result = new Result();

	public final String RESULT_SUCCESS = "200";
	public final String RESULT_FAIL = "204";
	
	public final String SEARCH_FAIL_MESSAGE = "조회된 정보가 없습니다.";
	public final String SEARCH_SUCCESS_MESSAGE = "정보가 조회 되었습니다.";
	
	public final String UPDATE_SUCCESS_MESSAGE = "정보가 변경이 성공 하였습니다.";
	public final String UPDATE_FAIL_MESSAGE = "정보 변경에 실패 하였습니다.";
	
	public final String DELETE_SUCCESS_MESSAGE = "정보가 삭제 되었습니다.";
	public final String DELETE_FAIL_MESSAGE = "정보 삭제중 오류가 발생 하였습니다.";
	
	public final String MEMBER_ID = "memberId";
	public final String MEMBER_PWD = "memberPwd";
	public final String MEMBER_CHANGE_PWD = "memberChangePwd";
	public final String CITY_CODE = "cityCode";
	public final String STATE_CODE = "stateCode";
	public final String MANAGE_TYPE = "manageType";
	public final String MANAGE_ID = "manageId";
	public final String SEARCH_TEXT = "searchText";
	
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception;
}