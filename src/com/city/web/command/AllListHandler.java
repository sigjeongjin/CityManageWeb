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
		List<SensorRegister> wmRegisterList = new ArrayList<>();
		List<SensorRegister> tmRegisterList = new ArrayList<>();
		List<SensorRegister> gmRegisterList = new ArrayList<>();
		List<SensorRegister> smRegisterList = new ArrayList<>();

		memberNameList = memberManageService.getMemberNameList();
		wmRegisterList = manageLocationService.getRegisterList(WM);
		tmRegisterList = manageLocationService.getRegisterList(TM);
		gmRegisterList = manageLocationService.getRegisterList(GM);
		smRegisterList = manageLocationService.getRegisterList(SM);

		request.setAttribute("wmRegisterList", wmRegisterList);
		request.setAttribute("tmRegisterList", tmRegisterList);
		request.setAttribute("gmRegisterList", gmRegisterList);
		request.setAttribute("smRegisterList", smRegisterList);
		request.setAttribute("memberNameList", memberNameList);

		return "index.jsp";
	}
}