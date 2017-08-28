package com.city.api.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.SensorService;
import com.city.model.SensorResultInfo;
import com.city.model.SensorResultListJSON;
import com.google.gson.Gson;

/**
 * @author com
 *
 */
public class StateSearchSensorListHandler implements CommandJsonHandler{
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

		String memberId = req.getParameter("memberId");
		String manageType = req.getParameter("manageType");
		String searchText = req.getParameter("searchText");
		
		List<SensorResultInfo> sensorResultInfoList = sensorService.getSensorListByState(memberId, manageType, searchText);
		
		SensorResultListJSON sensorResultListJSON = new SensorResultListJSON();
		sensorResultListJSON.setSensorList(sensorResultInfoList);
		sensorResultListJSON.setResultCode("200");
		sensorResultListJSON.setResultMessage("조회 되었습니다.");
		Gson gson = new Gson();
		return gson.toJson(sensorResultListJSON);
	}
}
