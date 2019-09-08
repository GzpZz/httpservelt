package com.config;

public class ClassMapping {
	private String url;
	private String className;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String toString() {
		return "ClassMapping [url=" + url + ", className=" + className + "]";
	}
}
