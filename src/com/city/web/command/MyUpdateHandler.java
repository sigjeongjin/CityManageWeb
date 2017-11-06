package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.Member;
import com.city.web.service.MemberManageService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class MyUpdateHandler implements CommandHandler {

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

	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return "view/member/memberUpdateForm.jsp";
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String saveFolder = "/upload";
		String realFolder = request.getServletContext().getRealPath(saveFolder); // saveFilepath
		int maxSize = 5 * 1024 * 1024; // 최대 업로될 파일크기 5Mb
		MultipartRequest multi = new MultipartRequest(request, realFolder, maxSize, "utf-8",
				new DefaultFileRenamePolicy());

		Member member = new Member();
		String memberId = (String) request.getSession().getAttribute("userId");
		member.setMemberId(memberId);
		member.setMemberPwd(multi.getParameter("newPwd"));
		member.setMemberName(multi.getParameter("newName"));
		member.setMemberPhone(multi.getParameter("newPhone"));
		member.setMemberEmail(multi.getParameter("newEmail"));
		member.setMemberPhoto(multi.getFilesystemName("newPhoto"));

		memberManageService.MemberUpdate(member);

		request.getSession().setAttribute("authMemberName", member.getMemberName());

		return "index.jsp";
	}
}