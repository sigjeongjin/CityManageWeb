package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.MemberManageService;

public class MemberPwdChangeHandler implements CommandJsonHandler {
	
	private MemberManageService memberManageService = new MemberManageService();

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		} else if (request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.processSubmit(request, response);
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String memberId = request.getParameter(MEMBER_ID);
		String memberPwd = request.getParameter(MEMBER_PWD);
		String memberNewPwd = request.getParameter(MEMBER_CHANGE_PWD);
	
		int resultCode = memberManageService.setMemberPwdChange(memberId, memberPwd, memberNewPwd);
		
		if (resultCode == 1){
			result.setResultCode(RESULT_SUCCESS);
			result.setResultMessage(UPDATE_SUCCESS_MESSAGE);
		} else {
			result.setResultCode(RESULT_FAIL);
			result.setResultMessage(UPDATE_FAIL_MESSAGE);
		}
		return gson.toJson(result);
	}
}