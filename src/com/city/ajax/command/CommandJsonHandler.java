package com.city.ajax.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public interface CommandJsonHandler {
	
	public final String MEMBER_ID 			= "memberId";
	public final String MEMBER_PWD 			= "memberPwd";
	public final String MEMBER_CHANGE_PWD 	= "memberChangePwd";
	public final String CITY_CODE 			= "cityCode";
	public final String STATE_CODE 			= "stateCode";
	public final String MANAGE_TYPE 		= "manageType";
	public final String MANAGE_ID 			= "manageId";
	
	public JSONObject process(HttpServletRequest request, HttpServletResponse response) throws Exception;
}