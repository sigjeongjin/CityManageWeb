package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.Member;
import com.city.web.service.MemberManageService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class MemberUpdateHandler implements CommandHandler {
	
	MemberManageService memberManageService = new MemberManageService();

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
		
		String saveFolder = "/upload";
		String realFolder = request.getServletContext().getRealPath(saveFolder); // saveFilepath
		System.out.println("realFolder : " + realFolder);
		int maxSize = 5 * 1024 * 1024; // 최대 업로될 파일크기 5Mb
		MultipartRequest multi = new MultipartRequest(request, realFolder, maxSize, "utf-8",
				new DefaultFileRenamePolicy());

		System.out.println("memberId : " + multi.getParameter("memberId"));
		System.out.println("memberPwd : " + multi.getParameter("memberPwd"));
		System.out.println("memberName : " + multi.getParameter("memberName"));
		System.out.println("memberPhone : " + multi.getParameter("memberPhone"));
		System.out.println("memberEmail : " + multi.getParameter("memberEmail"));
		System.out.println("memberPhoto : " + multi.getFilesystemName("memberPhoto"));
		
		Member member = new Member();
		member.setMemberId(request.getParameter("memberId"));
		member.setMemberPwd(multi.getParameter("memberPwd"));
		member.setMemberName(multi.getParameter("memberName"));
		member.setMemberPhone(multi.getParameter("memberPhone"));
		member.setMemberEmail(multi.getParameter("memberEmail"));
		member.setMemberPhoto(multi.getFilesystemName("memberPhoto"));

		memberManageService.MemberUpdate(member);

		return "/wmList.do";	
	}
}
