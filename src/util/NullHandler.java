package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.city.api.command.CommandJsonHandler;

public class NullHandler implements CommandJsonHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res)
	throws Exception {
		res.sendError(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}

}
