package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.MemberManageService;
import com.city.api.service.PushService;
import com.city.model.PushInfo;

public class PushTokenUpdateHandler implements CommandJsonHandler{

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
		
		// Android에서 pushToken, memberId 가져옴
		String memberId = request.getParameter("memberId");
		
		//memberId로 조회하여 memberPhone가져옴
		String memberPhone = memberManageService.memberPhoneSelect(memberId);
		
		PushInfo pushInfo = new PushInfo();
		
		pushInfo.setPushToken(request.getParameter("pushToken"));
		pushInfo.setMemberId(request.getParameter("memberId"));
		pushInfo.setMemberPhone(memberPhone);
		
		int resultCode = pushService.pushTokenUpdate(pushInfo);
		if (resultCode == 1) {
			result.setResultCode(RESULT_SUCCESS);
			result.setResultMessage("push update success");
		} else {
			result.setResultCode(RESULT_FAIL);
			result.setResultMessage("push update fail");
		}
		return gson.toJson(result);
	}
}
