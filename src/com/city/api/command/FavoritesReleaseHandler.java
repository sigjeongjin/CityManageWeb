package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.FavoritesService;

public class FavoritesReleaseHandler implements CommandJsonHandler {
	private FavoritesService favoritesService = new FavoritesService();

	@Override
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

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {

		String memberId = req.getParameter(MEMBER_ID);
		String manageId = req.getParameter(MANAGE_ID);

		int resultCode = favoritesService.setFavoritesRelease(memberId, manageId);

		if (resultCode == 1) {
			result.setResultCode(RESULT_SUCCESS);
			result.setResultMessage("즐겨찾기 해제 되었습니다.");
		} else {
			result.setResultCode(RESULT_FAIL);
			result.setResultMessage("즐겨찾기 해제에 실패 하였습니다.");
		}

		return gson.toJson(result);
	}

}