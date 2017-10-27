package com.city.api.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.MemberManageService;
import com.city.model.Member;
import com.city.model.Result;
import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class MemberRegisterHandler implements CommandJsonHandler {

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

	private String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.processSubmit(request, response);
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {		
	
		Gson gson = new Gson();
		Result result = new Result();
		
		MultipartRequest multi;
		String saveFolder = "/upload";
//		String realFolder = request.getServletContext().getRealPath(saveFolder); // saveFilepath
		String realFolder = request.getServletPath();
		String realFolder1 = request.getServletContext().getContextPath();
		String realFolder2 = request.getServletContext().getRealPath("");
		
		System.out.println("realFolder : " + realFolder);
		System.out.println("realFolder : " + realFolder1);
		System.out.println("realFolder : " + realFolder2);
		
		
		return null;
//		int maxSize = 5 * 1024 * 1024; // 최대 업로될 파일크기 5Mb
//		
//		Member member = new Member();
//		try {
//			multi = new MultipartRequest(request, realFolder, maxSize, "utf-8",
//					new DefaultFileRenamePolicy());
//			member.setMemberId(multi.getParameter("memberId"));
//			member.setMemberPwd(multi.getParameter("memberPwd"));
//			member.setMemberName(multi.getParameter("memberName"));
//			member.setMemberPhone(multi.getParameter("memberPhone"));
//			member.setMemberEmail(multi.getParameter("memberEmail"));
//			member.setMemberPhoto(multi.getParameter("memberPhoto"));
//			member.setMemberAuthorization("app_user");
//			member.setMemberPhotoOriginal(realFolder + "\\" + multi.getParameter("memberPhoto"));
//								
//			String mR = memberManageService.memberRegister(member);
//			if (mR == "Y") {
//				result.setResultCode("200");
//				result.setResultMessage("회원가입을 환영합니다.");
//			}
//			return gson.toJson(result);
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
	}
}