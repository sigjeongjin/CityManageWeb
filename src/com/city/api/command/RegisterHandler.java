package com.city.api.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.RegisterService;
import com.city.model.Member;
import com.city.model.Result;
import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class RegisterHandler implements CommandJsonHandler {

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

	private String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.processSubmit(request, response);
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartRequest multi;
		
		String saveFolder = "/upload";
		String realFolder = request.getServletContext().getRealPath(saveFolder); // saveFilepath
		int maxSize = 5 * 1024 * 1024; // 최대 업로될 파일크기 5Mb
		
		Member member = new Member();
		try {
			multi = new MultipartRequest(request, realFolder, maxSize, "utf-8",
					new DefaultFileRenamePolicy());
			member.setMemberId(multi.getParameter("memberId"));
			member.setMemberPwd(multi.getParameter("memberPwd"));
			member.setMemberName(multi.getParameter("memberName"));
			member.setMemberPhone(multi.getParameter("memberPhone"));
			member.setMemberEmail(multi.getParameter("memberEmail"));
			member.setMemberPhoto(multi.getParameter("memberPhoto"));
			//member.setMemberPhoto(multi.getFilesystemName("memberPhoto"));
			member.setMemberAuthorization("app_user");
			
			Result result = new Result();
			result.setResultCode(registerService.register(member));
			
			if (result.getResultCode() == "200") {
				result.setResultMessage("success");
			}
			Gson gson = new Gson();
			return gson.toJson(result);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
