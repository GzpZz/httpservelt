package com.response;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.cookie.Cookie;

public class HttpResponseImpl implements HttpResponse {
	
	private Map<String,List<String>> headers = new HashMap<String,List<String>>();
	private List<Cookie> cookies = new ArrayList<>();
	private HttpResponseOutputStream out;
	private String version = "HTTP/1.1";
	private String status = "200 OK";
	private String charset;
	private boolean isCommited = false;
	
	
	{//默认几个头部字段。
		 this.setHeader("Host", "localhost");
		 this.setHeader("Server", "MyHttpServer1.1");
	}
	
	public HttpResponseImpl(OutputStream out) {
		this.out = new HttpResponseOutputStream(this, out);
	}
	
	@Override
	public OutputStream getOutputStream() {
		return out;
	}

	@Override
	public PrintWriter getWriter() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
	}

	public OutputStream getOut() {
		return out;
	}

	public void setOut(HttpResponseOutputStream out) {
		this.out = out;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public void setContentLength(int length) {
		setHeader("Content-Length", length+"");
	}
	
	@Override
	public void setContentLength(long length) {
		setHeader("Content-Length", length+"");
	}

	@Override
	public void setContentType(String contentType) {
		setHeader("Content-Type", contentType);
	}

	@Override
	public void setBufferSize(int bufSize) {
		out.setBufferSize(bufSize);
	}

	@Override
	public void flushBuffer() {
		try {
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isCommitted() {
		return isCommited;
	}

	public void setCommited(boolean isCommited) {
		this.isCommited = isCommited;
	}

	@Override
	public void setHeader(String key, String value) {
		List<String> values = headers.get(key);
		if(values == null) {
			values = new ArrayList<String>();
			headers.put(key, values);
			values.add(value);
		}else {
			values.set(0, value);
		}
	}

	@Override
	public void addHeader(String key, String value) {
		List<String> values = headers.get(key);
		if(values == null) {
			values = new ArrayList<String>();
			headers.put(key, values);
		}
		values.add(value);
	}

	@Override
	public void addCookie(Cookie cookie) {
		if(cookie != null) {
			cookies.add(cookie);
		}
	}
	
	public String getHeader() {
		StringBuffer sb = new StringBuffer();
		sb.append(version).append(" ").append(status).append("\r\n");
		for(Entry<String, List<String>> header  :headers.entrySet()) {
			String key = header.getKey();
			List<String> values = header.getValue();
			if(key.equals("Content-Type")) {
				if(charset != null && !charset.equals("")) {
					sb.append(key).append(": ").append(values.get(0))
						.append(";charset=").append(charset).append("\r\n");
				}
			}
			else {
				for(String value : values) {
					sb.append(key).append(": ").append(value).append("\r\n");
				}
			}
		}
		for(Cookie c : cookies) {
			sb.append("Set-Cookie: ").append(c.toCookieString()).append("\r\n");
		}
		sb.append("\r\n");
		return sb.toString();
	}

	@Override
	public void resetBuffer() {
		out.clearBuffer();
	}

	@Override
	public void sendRedirect(String url) {
		setStatus("302 Found");
		setHeader("Location", url);
	}

	@Override
	public void setCharacterEncoding(String encoding) {
		this.charset = encoding;
	}

	@Override
	public String getCharacterEncoding() {
		return charset;
	}
	
}
