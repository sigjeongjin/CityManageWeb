package com.city.api.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.FavoritesService;
import com.city.model.FavoritesResultInfo;
import com.city.model.FavoritesResultListJSON;

public class FavoritesListHandler implements CommandJsonHandler {
	private FavoritesService favoritesRegisterService = new FavoritesService();

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
	 * @param manageType(wm, tm, sm, gm)
	 * @return
	 * @throws Exception
	 */
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {

		String memberId = req.getParameter(MEMBER_ID);
		String manageType = req.getParameter(MANAGE_TYPE);
		
		List<FavoritesResultInfo> favoritesList = favoritesRegisterService.getFavoritesList(memberId,manageType);
		FavoritesResultListJSON favoritesInfoJSON = new FavoritesResultListJSON();
		
		if(favoritesList != null) {
			favoritesInfoJSON.setResultCode(RESULT_SUCCESS);
			favoritesInfoJSON.setResultMessage(SEARCH_SUCCESS_MESSAGE);
			favoritesInfoJSON.setFavoritesList(favoritesList);
		} else {
			favoritesInfoJSON.setResultCode(RESULT_FAIL);
			favoritesInfoJSON.setResultMessage(SEARCH_FAIL_MESSAGE);
		}

		return gson.toJson(favoritesInfoJSON);

	}
}