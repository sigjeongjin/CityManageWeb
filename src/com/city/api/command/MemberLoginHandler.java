package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.MemberManageService;
import com.city.model.Result;
import com.google.gson.Gson;

public class MemberLoginHandler implements CommandJsonHandler {

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

		Gson gson = new Gson();
		Result result = new Result();
		
		String memberId = request.getParameter("memberId");
		String memberPwd = request.getParameter("memberPwd");
		
		try {

			String mL = memberManageService.memberLogin(memberId, memberPwd);
			if (mL == "Y") {
				result.setResultCode("200");
				result.setResultMessage("로그인을 환영 합니다.");
			} else if (mL == "N"){
				result.setResultCode("204");
				result.setResultMessage("아이디 또는 비밀번호를 다시 확인하세요.");
			}
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(result);
	}
}
