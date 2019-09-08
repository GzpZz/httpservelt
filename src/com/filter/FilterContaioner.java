package com.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

public class FilterContaioner {
	
	private static final List<FilterConfig> configList = new ArrayList<>();
	
	private static final Map<String, HttpFilter> filterMap = new HashMap<>();
	
	public static void init(Element root) {
		if(root == null) return;
		for (Iterator<Element> i = root.elementIterator("filter"); i.hasNext();) {
			Element child = i.next();
			FilterConfig config = new FilterConfig();
			for(Element e : child.elements("url")) {
				config.addUrl(e.getTextTrim());
			}
			config.setClassName(child.elementTextTrim("className"));
			for(Element e : child.elements("param")) {
				config.addParameter(e.elementTextTrim("paramName"), e.elementTextTrim("paramValue"));
			}
			configList.add(config);
		}
	}
	
	public static List<HttpFilter> getFiltersByURI(String uri) {
		List<FilterConfig> fcList = new ArrayList<>();
		for(FilterConfig fc : configList) {
			for(String u : fc.getUrlList()) {
				if(u.equals(uri)) {
					fcList.add(fc);
					break;
				}
			}
		}
		List<HttpFilter> filterList = new ArrayList<>();
		for(FilterConfig fc : fcList) {
			HttpFilter filter = getFilterByClassName(fc.getClassName());
			if(filter != null) {
				filter.init(fc);
				filterList.add(filter);
			}
		}
		return filterList;
	}
	
	private static HttpFilter getFilterByClassName(String className) {
		HttpFilter filter = filterMap.get(className);
		if(filter == null) {
			synchronized (filterMap) {
				filter = filterMap.get(className);
				if(filter == null) {
					try {
						filter = (HttpFilter)Class.forName(className).newInstance();
						filterMap.put(className, filter);
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return filter;
	}
	
}
