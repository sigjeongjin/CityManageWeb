package mvc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.StringUtils;

/**
 * Servlet Filter implementation class SessionCheck
 */
@WebFilter("/*")
public class SessionCheck implements Filter {

	private List<String> noFilterUrl;
	
	private List<String> resourcePath;
	
    /**
     * Default constructor. 
     */
    public SessionCheck() {
        noFilterUrl = new ArrayList<String>();
        noFilterUrl.add("/");
        noFilterUrl.add("/login.do");
        noFilterUrl.add("/register.do");
        noFilterUrl.add("/logout.do");
        noFilterUrl.add("/memberLogin.app");
        noFilterUrl.add("/memberRegister.app");
		noFilterUrl.add("/sensorInfoRegister.app");
		noFilterUrl.add("/stateInfo.app");
		noFilterUrl.add("/cityStateInfoRegister.app");
		noFilterUrl.add("/operationStatusRegister.app");
		noFilterUrl.add("/favoritesRegister.app");
		noFilterUrl.add("/favoritesList.app");
		noFilterUrl.add("/cityInfo.app");
		noFilterUrl.add("/memberPwdConfirm.app");
		noFilterUrl.add("/memberPwdChange.app");
		noFilterUrl.add("/memberProfileImageChange.app");
		noFilterUrl.add("/pushHistoryList.app");
		noFilterUrl.add("/sensorList.app");
		noFilterUrl.add("/stateSearchSensorList.app");
		noFilterUrl.add("/favoritesWhether.app");
		noFilterUrl.add("/wmInfo.app");
		noFilterUrl.add("/tmInfo.app");
		noFilterUrl.add("/gmInfo.app");
		noFilterUrl.add("/smInfo.app");
		
		
        resourcePath = new ArrayList<String>();
        resourcePath.add("/css/");
        resourcePath.add("/js/");
        resourcePath.add("/view/");
        
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		String uri = req.getRequestURI();
		
		if(!noFilterUrl.contains(uri)) {
			
			boolean isResourcePath = false;
			
			for(String resource : resourcePath) {
				if(uri.startsWith(resource)) {
					isResourcePath = true;
					break;
				}
			}
			
			if(!isResourcePath) {
				HttpSession session = req.getSession();
				
				//session userId
				// session userName
				
				String memberId = (String)session.getAttribute("userId");
				if(StringUtils.isNullOrEmpty(memberId)) {
					HttpServletResponse res = (HttpServletResponse) response;
					res.sendRedirect("/login.do");
					return;
				}
			}
			
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
