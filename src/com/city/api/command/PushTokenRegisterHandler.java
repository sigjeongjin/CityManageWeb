package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.PushService;
import com.city.model.PushResultListJSON;
import com.google.gson.Gson;

public class PushTokenRegisterHandler {

	private PushService pushService = new PushService();

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
	 * @param memberId
	 * @param pushToken
	 * @return
	 * @throws Exception
	 */
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String memberId = request.getParameter("memberId");
		String pushToken = request.getParameter("pushToken");
		
		System.out.println("memberId : " + memberId);
		System.out.println("pushToken : " + pushToken);

		//List<PushResultInfo> pushInfoList = pushService.getPushHistoryList(memberId, manageType);
		PushResultListJSON pushListJSON = new PushResultListJSON();

		//pushListJSON.setResultCode("200");
		//pushListJSON.setResultMessage("조회되었습니다.");
		//pushListJSON.setPushHistoryList(pushInfoList);

		Gson gson = new Gson();
		return gson.toJson(pushListJSON);
	}

}
