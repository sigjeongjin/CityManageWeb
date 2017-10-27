package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.MemberManageService;
import com.city.api.service.PushService;
import com.city.model.Push;
import com.city.model.Result;
import com.google.gson.Gson;

public class PushTokenUpdateHandle implements CommandJsonHandler{

	private PushService pushService = new PushService();
	private MemberManageService memberManageService = new MemberManageService();

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

	/**
	 * @param pushToken
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Gson gson = new Gson();
		Result result = new Result();
		
		// Android에서 pushToken, memberId 가져옴
		String memberId = request.getParameter("memberId");
		String memberPhone = memberManageService.memberPhoneSelect(memberId); //memberId로 조회하여 memberPhone가져옴
		
		Push push = new Push();
		push.setPushToken(request.getParameter("pushToken"));
		push.setMemberId(request.getParameter("memberId"));
		push.setMemberPhone(memberPhone);
		
		String pushRegister = pushService.pushTokenUpdate(push);
		if (pushRegister == "Y") {
			result.setResultCode("200");
			result.setResultMessage("push update success");
		} else {
			result.setResultCode("400");
			result.setResultMessage("push update fail");
		}
		return gson.toJson(result);
	}
}
