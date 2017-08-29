package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.FavoritesService;
import com.city.model.Result;
import com.google.gson.Gson;

public class FavoritesRegisterHandler implements CommandJsonHandler {	
	private FavoritesService favoritesRegisterService = new FavoritesService();

@Override
public String process(HttpServletRequest req, HttpServletResponse res)
		throws Exception {
	if (req.getMethod().equalsIgnoreCase("GET")) {
		return processForm(req, res);
	} else if (req.getMethod().equalsIgnoreCase("POST")) {
		return processSubmit(req, res);
	} else {
		res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		return null;
	}
}
private String processForm(HttpServletRequest req, HttpServletResponse res)
		throws Exception {
	return this.processSubmit(req, res);
}

private String processSubmit(HttpServletRequest req, HttpServletResponse res)
		throws Exception {
	
	Result result = new Result();

	String memberId = req.getParameter("memberId");
	String bookmark = req.getParameter("bookmark");
	String manageId = req.getParameter("manageId");

	String resultCode = favoritesRegisterService.favoritesRegister(memberId, bookmark, manageId);
	
	if(resultCode.equals("Y")){
		result.setResultCode("200");
		result.setResultMessage("변경되었습니다.");
	} else {
		result.setResultCode("400");
		result.setResultMessage("조회실패");
	}
	
	Gson gson = new Gson();
	return gson.toJson(result);

}

}