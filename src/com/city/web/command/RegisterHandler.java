package com.city.web.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.model.CityAjaxJSON;
import com.city.model.Member;
import com.city.web.service.AddressService;
import com.city.web.service.MemberManageService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class RegisterHandler implements CommandHandler {

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

		AddressService addressService = new AddressService();
		
		List<CityAjaxJSON> addressCityList = new ArrayList<>();
		addressCityList = addressService.getCityList();
		
		request.setAttribute("city", addressCityList);
		
		return "/view/member/registerForm.jsp";
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {

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
			member.setMemberPwd(multi.getParameter("memberPwd"));
			member.setMemberName(multi.getParameter("memberName"));
			member.setMemberPhone(multi.getParameter("memberPhone"));
			member.setMemberEmail(multi.getParameter("memberEmail"));
			member.setMemberPhoto(multi.getFilesystemName("memberPhoto"));
			member.setCityCode(multi.getParameter("cityCode"));
			member.setMemberAuthorization("admin");
			member.setMemberPhotoOriginal(dbSaveFolder + "/" + saveFolder + "/" + multi.getParameter("memberPhoto"));
			
			memberManageService.setMember(member);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "/index.jsp";
	}
}
