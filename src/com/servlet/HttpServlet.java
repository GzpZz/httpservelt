package com.servlet;

import com.request.HttpRequest;
import com.response.HttpResponse;

public class HttpServlet {
	
	protected void doGet(HttpRequest rq, HttpResponse rp) { }
	protected void doPost(HttpRequest rq, HttpResponse rp) { }
	
	public void service(HttpRequest rq, HttpResponse rp) {
		String method = rq.getMethod().substring(0, 1).toUpperCase().concat(rq.getMethod().substring(1, rq.getMethod().length()).toLowerCase());
		try {
			HttpServlet.class.getDeclaredMethod("do"+method, HttpRequest.class, HttpResponse.class)
			.invoke(this, rq, rp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
