package com.request;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cookie.Cookie;
import com.seesion.Session;

public class HttpRequestImpl implements HttpRequest {
	
	private String method;
	private String uri;
	private String requestUri;
	private String queryString;
	private String version;
	private String charset;
	private InputStream in;
	private Map<String, String> headersMap = new HashMap<String,String>();
	private Map<String,List<String>> parametersMap = new  HashMap<String,List<String>>();
	private List<Cookie> cookies = new ArrayList<>();
	private String servletPath;
	private RequestDispatcherImpl dispatcher;
	private Map<String, Object> attributes = new HashMap<>();
	
	public HttpRequestImpl() {
		dispatcher = new RequestDispatcherImpl(this::setServletPath);
	}
	
	@Override
	public String getMethod() {
		return method;
	}

	@Override
	public String getUri() {
		return uri;
	}

	@Override
	public String getRequestUri() {
		return requestUri;
	}

	@Override
	public String getQueryString() {
		return queryString;
	}

	@Override
	public String getVersion() {
		return version;
	}
	@Override
	public Map<String, String> getHeadersMap() {
		return headersMap;
	}

	private String contentType;
	@Override
	public String getContentType() {
		return contentType;
	}

	private int contentLength;
	@Override
	public int getContentLength() {
		return contentLength;
	}

	@Override
	public Map<String, List<String>> getParametersMap() {
		return parametersMap;
	}

	@Override
	public String getParameter(String key) {
		 List<String> list = this.getParameterValues(key);
		 return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<String> getParameterValues(String key) {
		List<String> list = parametersMap.get(key);
		if(list ==null) {
			list = new ArrayList<String>();
			parametersMap.put(key, list);
		}
		return list;
	}
	
	@Override
	public InputStream getInputStream() {
		return this.in;
	}

	public InputStream getIn() {
		return in;
	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setHeadersMap(Map<String, String> headersMap) {
		this.headersMap = headersMap;
	}

	public void setParametersMap(Map<String, List<String>> parametersMap) {
		this.parametersMap = parametersMap;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	@Override
	public List<Cookie> getCookies() {
		return cookies;
	}
	
	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}
	
	public void addCookie(Cookie cookie) {
		cookies.add(cookie);
	}
/*
	@Override
	public void setCharacterEncoding(String encoding) {
		Map<String,List<String>> temp = new  HashMap<String,List<String>>();
		Iterator<Map.Entry<String,List<String>>> it = parametersMap.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String,List<String>> entry = it.next();
			String key = entry.getKey();
			try {
				key = new String(key.getBytes(), encoding);
			} catch (UnsupportedEncodingException e) {
				key = entry.getKey();
			}
			List<String> values = entry.getValue();
			ListIterator<String> listIt = values.listIterator();
			while(listIt.hasNext()) {
				String value = listIt.next();
				try {
					value = new String(value.getBytes(), encoding);
					listIt.set(value);
				} catch (UnsupportedEncodingException e) {
					//什么都不用做
				}
			}
			temp.put(key, values);
		}
		parametersMap = temp;
	}
*/
	@Override
	public String getServletPath() {
		return this.servletPath;
	}
	
	public void setServletPath(String path) {
		this.servletPath = path;
	}
	
	@Override
	public RequestDispatcher getRequestDispatcher(String url) {
		dispatcher.setPath(url);
		return dispatcher;
	}

	@Override
	public String getDispatcherType() {
		return dispatcher.getDispatcherType();
	}

	@Override
	public void setAttribute(String key, Object value) {
		attributes.put(key, value);
	}

	@Override
	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	@Override
	public void removeAttribute(String key) {
		attributes.remove(key);
	}

	@Override
	public void setCharacterEncoding(String encoding) {
		charset = encoding;
	}
	
	@Override
	public String getCharacterEncoding() {
		return charset;
	}

	@Override
	public Session getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session getSession(boolean create) {
		// TODO Auto-generated method stub
		return null;
	}

}
