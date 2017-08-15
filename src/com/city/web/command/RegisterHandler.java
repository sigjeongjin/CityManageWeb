package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.Member;
import com.city.web.service.RegisterService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;


public class RegisterHandler implements CommandHandler {

	private RegisterService registerService = new RegisterService();

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

		return "view/registerForm.jsp";
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) 
	throws Exception {
		
//		String savePath = request.getServletContext().getRealPath("pic");
//		int maxSize = 5*1024*1024;  //최대 업로될 파일크기 5Mb
//		MultipartRequest multi = new MultipartRequest(request, savePath, maxSize, "utf-8", new DefaultFileRenamePolicy());
//		
//		String fileName = multi.getFilesystemName("memberPhoto");
//		String fileFullPath = savePath + "/" + fileName;
	
		Member member = new Member();
		member.setMemberId(request.getParameter("memberId"));
		member.setMemberPwd(request.getParameter("memberPwd"));
		member.setMemberName(request.getParameter("memberName"));
		member.setMemberPhone(request.getParameter("memberPhone"));
		member.setMemberEmail(request.getParameter("memberEmail"));
		member.setMemberPhoto(request.getParameter("memberPhoto"));
//		member.setMemberPhoto(multi.getFilesystemName("memberPhoto"));
//		member.setMemberPhoto(fileFullPath);
		member.setMemberAuthorization(request.getParameter("memberAuthorization"));
//		member.setMemberDeleteCode(request.getParameter("memberDeleteCode"));
		member.setCityGeocode(request.getParameter("cityGeocode"));
		member.setStateGeocode(request.getParameter("stateGeocode"));

		registerService.register(member);
		request.setAttribute("memberJoin", member);
		return "view/welcomeForm.jsp";
	}
}
