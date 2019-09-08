package com.filter;

import com.request.HttpRequest;
import com.response.HttpResponse;

public interface HttpFilterChain {
	void doFilter(HttpRequest rq, HttpResponse rp);
}
