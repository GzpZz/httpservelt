package com.cookie;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Cookie {
	private String name;
	private String value;
	private String path = "/";
	private String expires;
	
	public Cookie() { }
	
	public Cookie(String key, String value) {
		this.name = key;
		this.value = value;
	}
	
	public Cookie(String name, String value, String path) {
		super();
		this.name = name;
		this.value = value;
		this.path = path;
	}

	public String getName() {
		return name;
	}
	public void setName(String key) {
		this.name = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getExpires() {
		return expires;
	}
	public void setExpires(String expires) {
		this.expires = expires;
	}
	
	public String toCookieString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(name).append("=").append(URLEncoder.encode(value, "utf-8")).append(";")
			.append("Path= ").append(path);
			if(expires != null && !expires.equals("")) {
				sb.append("Expires= ").append(expires);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return "Cookie [name=" + name + ", value=" + value + ", path=" + path + ", expires=" + expires + "]";
	}
	
}
