package com.response;

import java.io.OutputStream;
import java.io.PrintWriter;

import com.cookie.Cookie;

public interface HttpResponse {
	
	OutputStream getOutputStream();
	PrintWriter getWriter();
	void setContentLength(int length);
	void setContentLength(long length);
	void setContentType(String contentType);
	void setBufferSize(int bufSize);
	void flushBuffer();
	boolean isCommitted();
	void setCharacterEncoding(String encoding);
	String getCharacterEncoding();
	
	void addCookie(Cookie cookie);
	
	void setHeader(String key,String value);
	void addHeader(String key,String value);
	
	void resetBuffer();
	
	void sendRedirect(String url);
	
}
