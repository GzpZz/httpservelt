package com.utils;

import java.util.HashMap;
import java.util.Map;

public class MIMEUtils {
	private static Map<String,String> mimemapping;
	static {
		mimemapping = new HashMap<String,String>();
		mimemapping.put("html", "text/html");
		mimemapping.put("js", "application/javascript");
		mimemapping.put("jpg", "image/jpeg");
		mimemapping.put("png", "image/png");
		mimemapping.put("css", "text/css");
		mimemapping.put("xml", "text/xml");
		mimemapping.put("jpeg", "image/jpeg");
		mimemapping.put("bmp", "image/bmp");
		mimemapping.put("gif", "image/gif");
		
	}
	
    public static String getMIME(String url) {
    	int index = url.lastIndexOf(".");
    	String suffix;
    	if(index != -1 ) {
    		suffix = url.substring( index+1, url.length() );
    	}else {
    		suffix = url;
    	}
		String value = mimemapping.get(suffix);
		if(value == null ) {
			//任意二进制数据的Content-Type：
			//浏览器针对这个Content-Type的行为：下载。
			value = "application/octet-stream";
		}
		return value;
    }
}
