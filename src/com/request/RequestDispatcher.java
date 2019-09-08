package com.request;

import com.response.HttpResponse;

public interface RequestDispatcher {
	final String DISPATCHER_FLAG = "QWERTYUIKJHGFDSAXCVB";
	void forward(HttpRequest rq, HttpResponse rp);
	void include(HttpRequest rq, HttpResponse rp);
}
