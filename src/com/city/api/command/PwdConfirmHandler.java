package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.RegisterService;
import com.city.model.Member;
import com.google.gson.Gson;

public class PwdConfirmHandler implements CommandJsonHandler {

	private RegisterService registerService = new RegisterService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	private String processForm(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		return this.processSubmit(req, res);
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		String memberId = req.getParameter("memberId");
		String memberPwd = req.getParameter("memberPwd");

		Member member = registerService.pwdConfirm(memberId, memberPwd);
		
		member.setResultCode("200");
		member.setResultMessage("확인되었습니다.");				
				
		
		Gson gson = new Gson();
		return gson.toJson(member);

	}

}