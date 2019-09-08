package web.servlet;

import java.io.IOException;

import com.request.HttpRequest;
import com.response.HttpResponse;
import com.servlet.HttpServlet;

public class TestServlet extends HttpServlet {

	@Override
	protected void doGet(HttpRequest rq, HttpResponse rp) {
		rq.setCharacterEncoding("utf-8");
		try {
			String name = rq.getParameter("user");
			System.out.println("username="+name);
			rp.getOutputStream().write(("do TestServlet "+name).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		rq.getRequestDispatcher("/demo").include(rq, rp);
	}

}
