package com.city.api.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.AddressCityService;
import com.city.api.service.FavoritesService;
import com.city.model.FavoritesInfo;
import com.city.model.FavoritesInfoJSON;
import com.city.model.SensorInfo;
import com.city.model.State;
import com.city.model.StateJSON;
import com.google.gson.Gson;

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

		String memberId = req.getParameter("memberId");
		String manageType = req.getParameter("manageType");
		
		List<FavoritesInfo> favoritesList = favoritesRegisterService.getFavoritesList(memberId,manageType);
		FavoritesInfoJSON favoritesInfoJSON = new FavoritesInfoJSON();
		favoritesInfoJSON.setResultCode("200");
		favoritesInfoJSON.setResultMessage("조회되었습니다.");
		favoritesInfoJSON.setFavoritesList(favoritesList);

		Gson gson = new Gson();
		return gson.toJson(favoritesInfoJSON);

	}
}