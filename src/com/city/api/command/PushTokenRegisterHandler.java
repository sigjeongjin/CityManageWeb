package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.PushService;
import com.city.model.Result;
import com.city.web.command.CommandHandler;
import com.google.gson.Gson;

public class PushTokenRegisterHandler implements CommandJsonHandler{

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
	 * @param pushToken
	 * @return
	 * @throws Exception
	 */
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Gson gson = new Gson();
		Result result = new Result();
		
		
		String pushToken = request.getParameter("pushToken");
		
		System.out.println("pushToken : " + pushToken);

		
		String pushRegister = pushService.pushTokenRegister(pushToken);
	


		return gson.toJson("");
	}

}
