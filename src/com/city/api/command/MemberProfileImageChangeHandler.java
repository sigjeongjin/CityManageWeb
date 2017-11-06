package com.city.api.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.MemberManageService;
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

		MultipartRequest multiRequest;
		String saveFolder = "/upload";
		String realFolder = request.getServletContext().getRealPath(saveFolder); // saveFilepath
		int maxSize = 5 * 1024 * 1024; // 최대 업로될 파일크기 5Mb

		try {
			multiRequest = new MultipartRequest(request, realFolder, maxSize, "utf-8", new DefaultFileRenamePolicy());
			String memberId = multiRequest.getParameter(MEMBER_ID);
			String memberPhoto = multiRequest.getParameter("memberPhoto");
			String mPC = memberManageService.memberPhotoChange(memberId, memberPhoto);

			if (mPC == "Y") {
				result.setResultCode(RESULT_SUCCESS);
				result.setResultMessage(UPDATE_SUCCESS_MESSAGE);
			} else {
				result.setResultCode(RESULT_FAIL);
				result.setResultMessage(UPDATE_FAIL_MESSAGE);
			}
			return gson.toJson(result);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}