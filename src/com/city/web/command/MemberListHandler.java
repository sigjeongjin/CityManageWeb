package com.city.web.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.Member;
import com.city.web.service.MemberManageService;

public class MemberListHandler implements CommandHandler{

	private MemberManageService memberManageService = new MemberManageService();

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
		
		System.out.println("들어왓니?");
		List<Member> memberList = memberManageService.MemberList();
		request.setAttribute("memberList", memberList);
		
		System.out.println("memberList" + memberList);
		return "/view/memberListView.jsp";

	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("들어왓니?");
		List<Member> memberList = new ArrayList<Member>();
		memberList = memberManageService.MemberList();
		request.setAttribute("memberList", memberList);
		
		System.out.println("memberList" + memberList);
		
		
		ArrayList<String> list = (ArrayList) request.getAttribute("memberList");

		System.out.println("memberList -aa" + memberList);
		
		for (int i = 0; i < list.size(); i++) {

			System.out.println(list.get(i) + "<br>");

		}

		
/*		List<Member> x = (ArrayList<Member>)request.getAttribute(...)*/
		return "/view/memberListView.jsp";
	}

	}


