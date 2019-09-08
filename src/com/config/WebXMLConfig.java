package com.config;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.filter.FilterContaioner;
import com.servlet.ServletContainer;

public class WebXMLConfig {

	public static void init() {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new File("web.xml"));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = document.getRootElement();
		ServletContainer.init(root);
		FilterContaioner.init(root);
	}
}
