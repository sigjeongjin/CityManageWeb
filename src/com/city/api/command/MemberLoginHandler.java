package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.city.api.service.MemberManageService;
import com.city.model.MemberAPI;

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

		MemberAPI memberAPI = new MemberAPI();
		
		String memberId = request.getParameter(MEMBER_ID);
		String memberPwd = request.getParameter(MEMBER_PWD);

		memberAPI = memberManageService.memberLogin(memberId, memberPwd, memberAPI);
		
		if (StringUtils.isNotEmpty(memberAPI.getMemberId())) {
			memberAPI.setResultCode(RESULT_SUCCESS);
			memberAPI.setResultMessage("로그인을 환영 합니다.");
		} else {
			memberAPI.setResultCode(RESULT_FAIL);
			memberAPI.setResultMessage("아이디 또는 비밀번호를 다시 확인하세요.");
		}
		return gson.toJson(memberAPI);
	}
}
