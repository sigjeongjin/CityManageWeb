package com.city.api.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.FavoritesService;
import com.city.model.Favorites;
import com.city.model.FavoritesResultListJSON;
import com.google.gson.Gson;

public class FavoritesWhetherHandler implements CommandJsonHandler {
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
		String manageId = req.getParameter("manageId");
		
		Favorites favorites= favoritesRegisterService.getFavoritesWhether(memberId,manageId);
		
		
		Gson gson = new Gson();
		return gson.toJson(favorites);

	}
}