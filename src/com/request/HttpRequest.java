package com.request;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.cookie.Cookie;
import com.seesion.Session;

public interface HttpRequest {
	String getMethod();
	String getUri();
	String getRequestUri();
	String getQueryString();
	String getVersion();
	Map<String,String> getHeadersMap();
	String getContentType();
	int getContentLength();
	Map<String,List<String>> getParametersMap();
	String getParameter(String key);
	List<String> getParameterValues(String key);
	InputStream getInputStream();
	List<Cookie> getCookies();
	void setCharacterEncoding(String encoding);
	String getCharacterEncoding();
	String getServletPath();
	RequestDispatcher getRequestDispatcher(String url);
	String getDispatcherType();
	void setAttribute(String key, Object value);
	Object getAttribute(String key);
	void removeAttribute(String key);
	Session getSession();
	Session getSession(boolean create);
}
