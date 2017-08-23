package com.city.api.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.dao.Result;
import com.city.api.service.RegisterService;
import com.city.model.Member;
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

		String saveFolder = "/upload";
		String realFolder = request.getServletContext().getRealPath(saveFolder); // saveFilepath
		System.out.println("realFolder : " + realFolder);
		int maxSize = 5 * 1024 * 1024; // 최대 업로될 파일크기 5Mb
		MultipartRequest multi = new MultipartRequest(request, realFolder, maxSize, "utf-8",
				new DefaultFileRenamePolicy());

		Member member = new Member();
		member.setMemberId(request.getParameter("memberId"));
		member.setMemberPwd(request.getParameter("memberPwd"));
		member.setMemberName(request.getParameter("memberName"));
		member.setMemberPhone(request.getParameter("memberPhone"));
		member.setMemberEmail(request.getParameter("memberEmail"));
		//member.setMemberPhoto(request.getFilesystemName("memberPhoto"));
		member.setMemberAuthorization(request.getParameter("memberAuthorization"));
		member.setCityCode(request.getParameter("cityCode"));
		member.setStateCode(request.getParameter("stateCode"));

		// String strId = registerService.register(member);

		try {
			Result result = new Result();
			result.setResultCode(registerService.register(member));

			if (result.getResultCode() == "200") {
				result.setResultMessage("success");
			}
			Gson gson = new Gson();
			return gson.toJson(result);
		} catch (Exception e) {
			System.out.println("에러");
		}
		return null;
	}
}
