package com.city.api.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.MemberManageService;
import com.city.model.Member;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class MemberProfileImageChangeHandler implements CommandJsonHandler {

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

		MultipartRequest multi;
		String saveFolder = "/upload";
		String realFolder = "/home/pi/apache-tomcat-8.5.20/webapps";
		String dbSaveFolder = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		int maxSize = 5 * 1024 * 1024; // 최대 업로될 파일크기 5Mb

		Member member = new Member();
		try {
			multi = new MultipartRequest(request, realFolder + saveFolder, maxSize, "utf-8",
					new DefaultFileRenamePolicy());
			member.setMemberId(multi.getParameter("memberId"));
			member.setMemberPhoto(multi.getParameter("memberPhoto"));
			member.setMemberAuthorization("app_user");
			member.setMemberPhotoOriginal(dbSaveFolder + "/" + saveFolder + "/" + multi.getParameter("memberPhoto"));
			
			int resultCode = memberManageService.memberPhotoChange(member);

			if (resultCode == 1) {
				result.setResultCode(RESULT_SUCCESS);
				result.setResultMessage(UPDATE_SUCCESS_MESSAGE);
			} else {
				result.setResultCode(RESULT_FAIL);
				result.setResultMessage(UPDATE_FAIL_MESSAGE);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return gson.toJson(result);
	}
}