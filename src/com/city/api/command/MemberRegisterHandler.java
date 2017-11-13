package com.city.api.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.service.MemberManageService;
import com.city.model.Member;
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
	
		MultipartRequest multi;
		String saveFolder = "/upload";
		String realFolder = request.getServletContext().getRealPath(saveFolder); // saveFilepath
		String dbSaveFolder = "";
		
		int maxSize = 5 * 1024 * 1024; // 최대 업로될 파일크기 5Mb
		
		Member member = new Member();
		try {
			multi = new MultipartRequest(request, realFolder + saveFolder, maxSize, "utf-8",
					new DefaultFileRenamePolicy());
			
			if(!realFolder.contains("workspace")) {
				realFolder = "/home/pi/apache-tomcat-8.5.20/webapps";
				dbSaveFolder = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			} else {
				dbSaveFolder = realFolder + "/" + multi.getFilesystemName("memberPhoto");
			}
			
			member.setMemberId(multi.getParameter("memberId"));
			member.setMemberPwd(multi.getParameter("memberPwd"));
			member.setMemberName(multi.getParameter("memberName"));
			member.setMemberPhone(multi.getParameter("memberPhone"));
			member.setMemberEmail(multi.getParameter("memberEmail"));
			member.setMemberPhoto(multi.getParameter("memberPhoto"));
			member.setMemberAuthorization("app_user");
			member.setMemberPhotoOriginal(dbSaveFolder + "/" + saveFolder + "/" + multi.getParameter("memberPhoto"));
								
			int resultCode = memberManageService.memberRegister(member);
			if (resultCode == 1) {
				result.setResultCode(RESULT_SUCCESS);
				result.setResultMessage("회원가입을 환영합니다.");
			} else {
				result.setResultCode(RESULT_FAIL);
				result.setResultMessage("회원가입에 실패 하였습니다.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gson.toJson(result);	
	}
}