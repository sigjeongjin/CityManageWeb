package com.city.web.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.Member;
import com.city.model.SensorRegister;

import com.city.web.service.ManageLocationService;
import com.city.web.service.MemberManageService;

public class AllListHandler implements CommandHandler {

	private MemberManageService memberManageService = new MemberManageService();
	private ManageLocationService manageLocationService = new ManageLocationService();

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
		return "index.jsp";
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Member> memberNameList = new ArrayList<>();
		memberNameList = memberManageService.memberNameList();	
		request.setAttribute("memberNameList", memberNameList);
		
		List <SensorRegister> tmRegisterList =  new ArrayList<>();
		tmRegisterList = manageLocationService.RegisterList("tm");
		request.setAttribute("tmRegisterList", tmRegisterList);
		
		List <SensorRegister> wmRegisterList =  new ArrayList<>();
		wmRegisterList = manageLocationService.RegisterList("wm");
		request.setAttribute("wmRegisterList", wmRegisterList);
		
		List <SensorRegister> gmRegisterList =  new ArrayList<>();
		gmRegisterList = manageLocationService.RegisterList("gm");
		request.setAttribute("gmRegisterList", gmRegisterList);
		
		List <SensorRegister> smRegisterList =  new ArrayList<>();
		smRegisterList = manageLocationService.RegisterList("sm");
		request.setAttribute("smRegisterList", smRegisterList);
		
		return "index.jsp";
	}
}
