
package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.AddressCityService;

public class AddressCityInfoHandler implements CommandJsonHandler {

	private AddressCityService addressCityService = new AddressCityService();

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
		
		String cityCode = req.getParameter("cityCode");
		String stateCode = req.getParameter("stateCode");
		String memberId = req.getParameter("memberId");
		String memberPwd = req.getParameter("memberPwd");
		
		String resultCode = addressCityService.getCityStateInfo(cityCode, stateCode, memberId, memberPwd);
		
		if(resultCode.equals("Y")){
			result.setResultCode("200");
			result.setResultMessage("변경되었습니다.");
		} else {
			result.setResultCode("400");
			result.setResultMessage("조회실패");
		}

		return gson.toJson(result);

	}
}
