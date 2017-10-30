package com.city.api.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.PushService;
import com.city.api.service.SensorService;
import com.city.model.Result;
import com.google.gson.Gson;

public class PushSendMessageHandle implements CommandJsonHandler{

	private PushService pushService = new PushService();
	private SensorService sensorService = new SensorService();
	
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		} else if (request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.processSubmit(request, response);
	}

	/**
	 * @param title
	 * @param contents
	 * @return
	 * @throws Exception
	 */
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Gson gson = new Gson();
		Result result = new Result();
		
		/* 1. Topic으로 sensorId:sensorValue */
		String sensorId = request.getParameter("sensorId");
		String sensorValue  = request.getParameter("sensorValue");
		
		/* 2. select문으로 sensorId를 통해 해당 sensorNoticeStandard 값 가져오기 */
		String sensorNoticeStandard = sensorService.readNoticeStandard(sensorId);
		System.out.println("sensorNoticeStandard : " + sensorNoticeStandard);
		
		/* 3. sensorNoticeStandard와  sensorValue 비교 (String to Integer) */
		int sensorValueInt = Integer.parseInt(sensorValue);
		int sensorNoticeStandardInt = Integer.parseInt(sensorNoticeStandard);
		
		/* 4. 값을 비교하여 sensor */
		if(sensorValueInt > sensorNoticeStandardInt) {
		
			int sensorStatus = sensorService.changeSensorStatus(sensorId);
			System.out.println("sensorI-In : " + sensorId);
			System.out.println("sensorStatus-In : " + sensorStatus);
			
				if(sensorStatus==1) {
					
				} else {
					result.setResultCode("400");
					result.setResultMessage("sensorStatus Fail");
				}
			
		} else {
			return null;
		}
		
		
		
//		/* title, contents는  Test 수질, 쓰레기통, 도시가스, 금연구역에 따라 달라짐 */
//		
//		String title = request.getParameter("title");
//		String contents = request.getParameter("contents");
//		System.out.println("title : " + title);
//		System.out.println("contenst : " + contents);
//		
//		ArrayList<String> tokenList = pushService.sendTokenList();
//		pushService.sendPush(tokenList, title, contents);
//		
//		if(tokenList != null) {
//			result.setResultCode("200");
//			result.setResultMessage("Push Success");
//		} else {
//			result.setResultCode("400");
//			result.setResultMessage("Push Fail");
//		}
	
		return gson.toJson(result);
	}
}