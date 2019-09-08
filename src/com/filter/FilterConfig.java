package com.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterConfig {
	
	private List<String> urlList = new ArrayList<String>();
	
	private String className;
	
	private Map<String, String> params = new HashMap<String, String>();
	
	public void addUrl(String url) {
		urlList.add(url);
	}

	public List<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	public void addParameter(String key, String value) {
		params.put(key, value);
	}
	
	public String getParameter(String key) {
		return params.get(key);
	}
	
}
