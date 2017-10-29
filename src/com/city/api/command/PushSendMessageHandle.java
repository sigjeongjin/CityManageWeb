package com.city.api.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.PushService;
import com.city.model.Result;
import com.google.gson.Gson;

public class PushSendMessageHandle implements CommandJsonHandler{

	private PushService pushService = new PushService();
	
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
	 * @param main
	 * @param message
	 * @return
	 * @throws Exception
	 */
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Gson gson = new Gson();
		Result result = new Result();
		
		/* Topic으로 sensorId:sensorValue
		/* title, contents는  Test*/
		
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		System.out.println("main : " + title);
		System.out.println("content : " + contents);
		
		ArrayList<String> tokenList = pushService.sendTokenList();
		pushService.sendPush(tokenList, title, contents);
		
		if(tokenList != null) {
			result.setResultCode("200");
			result.setResultMessage("Push Success");
		} else {
			result.setResultCode("400");
			result.setResultMessage("Push Fail");
		}
	
		return gson.toJson(result);
	}
}