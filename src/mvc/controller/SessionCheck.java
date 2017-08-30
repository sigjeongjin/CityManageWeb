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
	
    /**
     * Default constructor. 
     */
    public SessionCheck() {
        noFilterUrl = new ArrayList<String>();
        noFilterUrl.add("/");
        noFilterUrl.add("/login.do");
        noFilterUrl.add("/register.do");
        noFilterUrl.add("/logout.do");
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
		
		System.out.println("Test 종료까지 URI 계속 찍음 : " + uri);
		
		if(!noFilterUrl.contains(uri)) {
			
			System.out.println("필터 작동");
			HttpSession session = req.getSession();
			
			//session userId
			// session userName
			
			String memberId = (String)session.getAttribute("userId");
			System.out.println(memberId);
			if(StringUtils.isNullOrEmpty(memberId)) {
				HttpServletResponse res = (HttpServletResponse) response;
				res.sendRedirect("/login.do");
				return;
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
