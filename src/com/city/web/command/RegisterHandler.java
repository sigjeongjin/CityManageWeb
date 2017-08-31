package com.city.web.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.city.model.CityAjaxJSON;
import com.city.model.Member;
import com.city.web.service.AddressService;
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

		AddressService addressService = new AddressService();
		
		List<CityAjaxJSON> addressCityList = new ArrayList<>();
		addressCityList = addressService.getCityList();
//
//		JSONArray jr = new JSONArray();
//		for (int i = 0; i < addressCityList.size(); i++) {
//			JSONObject object = new JSONObject();
//
//			object.put("stateCode", addressCityList.get(i).getCityCode());
//			object.put("stateName", addressCityList.get(i).getCityName());
//			jr.add(object);
//		}
//
//		JSONObject objectCity = new JSONObject();
//		objectCity.put("cityList", jr);
		
		request.setAttribute("city", addressCityList);
		
		return "view/member/registerForm.jsp";
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
		member.setMemberId(multi.getParameter("memberId"));
		member.setMemberPwd(multi.getParameter("memberPwd"));
		member.setMemberName(multi.getParameter("memberName"));
		member.setMemberPhone(multi.getParameter("memberPhone"));
		member.setMemberEmail(multi.getParameter("memberEmail"));
		member.setMemberPhoto(multi.getFilesystemName("memberPhoto"));
		member.setMemberAuthorization("admin");

		registerService.register(member);
		
		return "index.jsp";

	}
}
