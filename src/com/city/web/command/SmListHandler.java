package com.city.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SmListHandler  implements CommandHandler {
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String pageNoVal = request.getParameter("pageNo");
//		int pageNo = 1;
//		if (pageNoVal != null) {
//			pageNo = Integer.parseInt(pageNoVal);
//		}
//		
//		MemberListPage memberListPage = memberManageService.getMemberListPage(pageNo);
//		request.setAttribute("memberListPage", memberListPage);
		//.getSession().
		String manageType = "sm";
		
		request.getSession().setAttribute("manageType", manageType);
		return "/view/smListView.jsp";
	}
}