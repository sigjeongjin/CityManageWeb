package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TmSensorRegisterHandler implements CommandJsonHandler {
	
		@Override
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
		
		private String processForm(HttpServletRequest request, HttpServletResponse response) {
			return null;
		}

		private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
			
			return null;
		}

}