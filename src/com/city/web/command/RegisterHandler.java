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

		return "view/changeMemberInfoForm.jsp";
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String saveFolder = "/upload";
		String realFolder = request.getServletContext().getRealPath(saveFolder); // saveFilepath
		System.out.println("realFolder : " + realFolder);
		int maxSize = 5 * 1024 * 1024; // 최대 업로될 파일크기 5Mb
		MultipartRequest multi = new MultipartRequest(request, realFolder, maxSize, "utf-8",
				new DefaultFileRenamePolicy());

		Member member = new Member();
		member.setMemberId(multi.getParameter("memberId"));
		member.setMemberPwd(multi.getParameter("memberPwd"));
		member.setMemberName(multi.getParameter("memberName"));
		member.setMemberPhone(multi.getParameter("memberPhone"));
		member.setMemberEmail(multi.getParameter("memberEmail"));
		member.setMemberPhoto(multi.getFilesystemName("memberPhoto"));
		member.setMemberAuthorization("ADMIN");
		member.setCityGeocode(multi.getParameter("cityGeocode"));
		member.setStateGeocode(multi.getParameter("stateGeocode"));

		registerService.register(member);
		return "index.jsp";	

	}
}
