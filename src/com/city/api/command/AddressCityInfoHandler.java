
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
		
		String cityCode = req.getParameter(CITY_CODE);
		String stateCode = req.getParameter(STATE_CODE);
		String memberId = req.getParameter(MEMBER_ID);
		String memberPwd = req.getParameter(MEMBER_PWD);
		
		int resultCode = addressCityService.setCityStateInfo(cityCode, stateCode, memberId, memberPwd);
		
		if(resultCode == 1){
			result.setResultCode(RESULT_SUCCESS);
			result.setResultMessage(UPDATE_SUCCESS_MESSAGE);
		} else {
			result.setResultCode(RESULT_FAIL);
			result.setResultMessage(UPDATE_FAIL_MESSAGE);
		}

		return gson.toJson(result);

	}
}
