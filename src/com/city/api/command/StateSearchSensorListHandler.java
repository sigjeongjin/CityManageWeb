package com.city.api.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.SensorService;
import com.city.model.SensorResultInfo;
import com.city.model.SensorResultListJSON;

/**
 * @author com
 *
 */
public class StateSearchSensorListHandler implements CommandJsonHandler {
	private SensorService sensorService = new SensorService();

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
	 * @param searchText
	 * @return
	 * @throws Exception
	 */
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {

		SensorResultListJSON sensorResultListJSON = new SensorResultListJSON();
		
		String memberId = req.getParameter(MEMBER_ID);
		String manageType = req.getParameter(MANAGE_TYPE);
		String searchText = req.getParameter(SEARCH_TEXT);

		List<SensorResultInfo> sensorResultInfoList = sensorService.getSensorListByState(memberId, manageType,
				searchText);
		
		if(sensorResultInfoList != null ) {
			sensorResultListJSON.setResultCode(RESULT_SUCCESS);
			sensorResultListJSON.setResultMessage(SEARCH_SUCCESS_MESSAGE);
			sensorResultListJSON.setSensorList(sensorResultInfoList);
		} else {
			sensorResultListJSON.setResultCode(RESULT_FAIL);
			sensorResultListJSON.setResultMessage(SEARCH_FAIL_MESSAGE);
		}
		
		return gson.toJson(sensorResultListJSON);
	}
}
