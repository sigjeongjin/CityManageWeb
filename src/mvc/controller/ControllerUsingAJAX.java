package mvc.controller;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.city.ajax.command.CommandJsonHandler;


public class ControllerUsingAJAX extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, CommandJsonHandler> commandHandlerMap = new HashMap<>();
	
	public void init() throws ServletException {
		String configFile = getInitParameter("configFile"); // web.xml에서 configFile을 getlintPatameter() method를 통해 값을 가져옴
		Properties prop = new Properties();	// Properties 인스턴스화
		String configFilePath = getServletContext().getRealPath(configFile); // 파일의 절대경로와 서버의 상대경로를 조합하기 위해
		try (FileReader fr = new FileReader(configFilePath)) {
			prop.load(fr);	// 경로를 통해 Properties 파일 안에 내용을 읽어드림
		} catch (IOException e) {
			throw new ServletException(e);
		}
		Iterator keylter = prop.keySet().iterator(); // key로 이루워진 iterator를 반환
		while (keylter.hasNext()) {
			String command  = (String) keylter.next(); // key 값을 가져옴
			String handlerClassName = prop.getProperty(command); // Properties에 key값을 이용해서 value 값을 가져옴
			try {
				Class<?> handleClass = Class.forName(handlerClassName); // handlerClassName 이용해 class Object 반환
				CommandJsonHandler handlerInstance = (CommandJsonHandler) handleClass.newInstance(); // class Object를 객체화하여 형변환
				commandHandlerMap.put(command, handlerInstance); // Map에 key(properties에서 가져온 key) value(CommandHandler) 저장
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) { 
				throw new ServletException(e);
			}
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String command = request.getRequestURI();
	
		if (command.indexOf(request.getContextPath())==0) {
			command = command.substring(request.getContextPath().length());// /hello.do
			
		}
		
		CommandJsonHandler handler = commandHandlerMap.get(command);
		
		JSONObject result = null;
		try {
			result = handler.process(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
		if (result != null) {
			response.setCharacterEncoding("utf-8");
        	response.setContentType("text/json");
            response.setHeader("Cache-control", "no-cache, no-store");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "-1");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type");
            response.setHeader("Access-Control-Max-Age", "86400");
			PrintWriter writer = response.getWriter();
			writer.print(result);
			writer.flush();
		}
	}
}
