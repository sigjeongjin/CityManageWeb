package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.FavoritesService;

public class FavoritesRegisterHandler implements CommandJsonHandler {
	private FavoritesService favoritesRegisterService = new FavoritesService();

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

		int resultCode = favoritesRegisterService.setFavoritesRegister(memberId, manageId);

		if (resultCode == 1) {
			result.setResultCode(RESULT_SUCCESS);
			result.setResultMessage("즐겨찾기로 등록 되었습니다.");
		} else {
			result.setResultCode(RESULT_FAIL);
			result.setResultMessage("즐겨찾기 등록에 실패 하였습니다.");
		}

		return gson.toJson(result);

	}

}