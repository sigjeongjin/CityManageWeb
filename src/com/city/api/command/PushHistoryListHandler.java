package com.city.api.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.FavoritesService;
import com.city.api.service.PushService;
import com.city.model.FavoritesInfo;
import com.city.model.FavoritesInfoJSON;
import com.city.model.Push;
import com.city.model.PushInfo;
import com.city.model.PushListJSON;
import com.google.gson.Gson;

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
		String memberId = req.getParameter("memberId");
		String manageType = req.getParameter("manageType");
		
		List<PushInfo> pushInfoList = pushService.getPushHistoryList(memberId,manageType);
		PushListJSON pushListJSON = new PushListJSON();
		
		pushListJSON.setResultCode("200");
		pushListJSON.setResultMessage("조회되었습니다.");
		pushListJSON.setPushHistoryList(pushInfoList);
		
		Gson gson = new Gson();
		return gson.toJson(pushListJSON);
	}
}