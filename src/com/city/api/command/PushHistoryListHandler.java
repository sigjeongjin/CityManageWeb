package com.city.api.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.PushService;
import com.city.model.PushResultInfo;
import com.city.model.PushResultListJSON;

public class PushHistoryListHandler implements CommandJsonHandler {

	private PushService pushService = new PushService();

	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return this.processSubmit(req, res);
	}

	/**
	 * @param memberId
	 * @param manageType
	 * @return
	 * @throws Exception
	 */
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String memberId = req.getParameter(MEMBER_ID);
		String manageType = req.getParameter(MANAGE_TYPE);
		
		List<PushResultInfo> pushInfoList = pushService.getPushHistoryList(memberId,manageType);
		PushResultListJSON pushListJSON = new PushResultListJSON();
		
		if(pushInfoList != null) {
			pushListJSON.setResultCode(RESULT_SUCCESS);
			pushListJSON.setResultMessage(SEARCH_SUCCESS_MESSAGE);
			pushListJSON.setPushHistoryList(pushInfoList);
		} else {
			pushListJSON.setResultCode(RESULT_FAIL);
			pushListJSON.setResultMessage(SEARCH_FAIL_MESSAGE);
		}
		
		return gson.toJson(pushListJSON);
	}
}