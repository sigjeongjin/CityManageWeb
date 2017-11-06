package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandHandler {
	
	final String TM = "tm"; 
	final String GM = "gm";
	final String SM = "sm";
	final String WM = "wm";
	
	public final String MEMBER_ID 			= "memberId";
	public final String MEMBER_PWD 			= "memberPwd";
	public final String MEMBER_CHANGE_PWD 	= "memberChangePwd";
	public final String CITY_CODE 			= "cityCode";
	public final String STATE_CODE 			= "stateCode";
	public final String MANAGE_TYPE 		= "manageType";
	public final String MANAGE_ID 			= "manageId";
	public final String SEARCH_TEXT 		= "searchText";
	
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception;
}

