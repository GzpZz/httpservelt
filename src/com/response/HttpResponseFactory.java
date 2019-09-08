package com.response;

import java.io.OutputStream;

public class HttpResponseFactory {
	public static HttpResponse getHttpResponse(OutputStream out) {
		HttpResponseImpl res = new HttpResponseImpl(out);
		
		return res;
	}
}
