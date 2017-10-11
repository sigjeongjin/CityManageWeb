package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.MemberManageService;
import com.city.model.Result;
import com.google.gson.Gson;

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

	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return this.processSubmit(request, response);
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {

		String memberId = request.getParameter("memberId");
		String memberNewPwd = request.getParameter("memberChangePwd");
		
		Gson gson = new Gson();
		Result result = new Result();
	
		String mNP = memberManageService.memberPwdChange(memberId, memberNewPwd);
		
		if (mNP == "Y"){
			result.setResultCode("200");
			result.setResultMessage("변경성공");
		} else {
			result.setResultCode("400");
			result.setResultMessage("변경실패");
		}
		return gson.toJson(result);
	}
}