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

		String memberId = request.getParameter("memberId");
		String memberPwd = request.getParameter("memberPwd");

		MemberAPI member = new MemberAPI();

		try {
			member = memberManageService.memberLogin(memberId, memberPwd);
			if (StringUtils.isNotEmpty(member.getMemberId())) {
				member.setResultCode("200");
				member.setResultMessage("로그인을 환영 합니다.");
			} else {
				member.setResultCode("204");
				member.setResultMessage("아이디 또는 비밀번호를 다시 확인하세요.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(member);
	}
}
