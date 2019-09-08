package com.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.config.ClassMapping;

public class ServletContainer {
	
	private static final List<ClassMapping> servletMapping = new ArrayList<ClassMapping>(10);
	
	private static final Map<String, HttpServlet> servletMap = new HashMap<>();
	
	public static void init(Element root) {
		if(root == null) return;
		for (Iterator<Element> i = root.elementIterator("servlet"); i.hasNext();) {
			Element child = i.next();
			ClassMapping mapping = new ClassMapping();
			mapping.setClassName(child.elementTextTrim("className"));
			mapping.setUrl(child.elementTextTrim("url"));
			servletMapping.add(mapping);
		}
	}
	
	public static HttpServlet getServletByURI(String uri) {
		String className = null;
		for (ClassMapping sm : servletMapping) {
			if (sm.getUrl().equals(uri)) {
				className = sm.getClassName();
				break;
			}
		}
		if(className != null && !className.equals("")) {
			return getServletByClassName(className);
		}
		return null;
	}
	
	private static HttpServlet getServletByClassName(String className) {
		HttpServlet servlet = servletMap.get(className);
		if(servlet == null) {
			synchronized (servletMap) {
				servlet = servletMap.get(className);
				if(servlet == null) {
					try {
						servlet = (HttpServlet)Class.forName(className).newInstance();
						servletMap.put(className, servlet);
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return servlet;
	}
	
}
