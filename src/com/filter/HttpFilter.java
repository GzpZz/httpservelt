package com.filter;

import com.request.HttpRequest;
import com.response.HttpResponse;

public interface HttpFilter {
	void init(FilterConfig config);
	void doFilter(HttpRequest rq, HttpResponse rp, HttpFilterChain chain);
}
