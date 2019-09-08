package web.servlet;

import java.io.IOException;

import com.request.HttpRequest;
import com.response.HttpResponse;
import com.servlet.HttpServlet;

public class DemoSetvlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpRequest rq, HttpResponse rp) {
		try {
			String name = rq.getParameter("user");
			rp.getOutputStream().write(("do TestServlet "+name).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
