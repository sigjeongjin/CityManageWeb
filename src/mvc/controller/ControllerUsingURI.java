package mvc.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.web.command.CommandHandler;




public class ControllerUsingURI extends HttpServlet {
	
	private Map<String, CommandHandler> commandHandlerMap = new HashMap<>();
	
	public void init() throws ServletException {
		String configFile = getInitParameter("configFile"); // web.xml에서 configFile을 getlintPatameter() method를 통해 값을 가져옴
		Properties prop = new Properties();	// Properties 인스턴스화
		String configFilePath = getServletContext().getRealPath(configFile); // 파일의 절대경로와 서버의 상대경로를 조합하기 위해
		System.out.println("configFilePath : "+configFilePath);
		try (FileReader fr = new FileReader(configFilePath)) {
			prop.load(fr);	// 경로를 통해 Properties 파일 안에 내용을 읽어드림
		} catch (IOException e) {
			throw new ServletException(e);
		}
		Iterator keylter = prop.keySet().iterator(); // key로 이루워진 iterator를 반환
		while (keylter.hasNext()) {
			String command  = (String) keylter.next(); // key 값을 가져옴
			
			System.out.println("command : "+command);
			
			String handlerClassName = prop.getProperty(command); // Properties에 key값을 이용해서 value 값을 가져옴
			System.out.println("handlerClassName : "+handlerClassName);
			try {
				Class<?> handleClass = Class.forName(handlerClassName); // handlerClassName 이용해 class Object 반환
				CommandHandler handlerInstance = (CommandHandler) handleClass.newInstance(); // class Object를 객체화하여 형변환
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
		System.out.println("request.getContextPath() : "+request.getContextPath());
	
		String command = request.getRequestURI();
		System.out.println("command : "+command);
	
		if (command.indexOf(request.getContextPath())==0) {
			command = command.substring(request.getContextPath().length());// /hello.do		
		}
		
		CommandHandler handler = commandHandlerMap.get(command);
		System.out.println("handler : "+handler);
		if (handler == null) {
			handler = new NullHandler();
		}
		String viewPage = null;
		try {
			viewPage = handler.process(request, response);
			System.out.println("viewPage : "+viewPage);
		} catch (Exception e) {
			throw new ServletException(e);
		}
		if (viewPage != null) {
			RequestDispatcher dispatcher =request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}

	}

}
