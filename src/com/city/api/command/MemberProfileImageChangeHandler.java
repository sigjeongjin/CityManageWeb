package com.city.api.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.MemberManageService;
import com.city.model.Result;
import com.google.gson.Gson;
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

	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return this.processSubmit(request, response);
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {

		MultipartRequest multi;
		String saveFolder = "/upload";
		String realFolder = request.getServletContext().getRealPath(saveFolder); // saveFilepath
		int maxSize = 5 * 1024 * 1024; // 최대 업로될 파일크기 5Mb
		
		Gson gson = new Gson();
		Result result = new Result();
		
		try {
			multi = new MultipartRequest(request, realFolder, maxSize, "utf-8",
					new DefaultFileRenamePolicy());
			String memberId = multi.getParameter("memberId");
			String memberPhoto = multi.getParameter("memberPhoto");
			String mPC = memberManageService.memberPhotoChange(memberId, memberPhoto);
			
		if (mPC == "Y") {
			result.setResultCode("200");
			result.setResultMessage("확인되었습니다.");
		}
		return gson.toJson(result);
		
	} catch (IOException e) {
		e.printStackTrace();
	}
	return null;
	}
}