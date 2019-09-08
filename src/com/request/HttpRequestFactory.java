package com.request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.config.AppConfig;
import com.cookie.Cookie;

public class HttpRequestFactory {
	
	public static HttpRequest getHttpRequest(InputStream inputStream) throws IOException {
		HttpRequestImpl rq = new HttpRequestImpl();
		
		ByteArrayOutputStream big = new ByteArrayOutputStream(4*1024);
		byte [] sm = new byte[512];
		String reqeustStr = null;
		while(true) {
			int len = inputStream.read(sm);
			big.write(sm, 0, len);
			reqeustStr = new String(big.toByteArray(),"ISO8859-1");
			if(reqeustStr.indexOf("\r\n\r\n") != -1)break;
		}
		String[] strArr = reqeustStr.split("\r\n\r\n");
		HttpRequestInputStream in = new HttpRequestInputStream(inputStream);
		if(strArr.length > 1) {
			in.prepend(strArr[1]);
		}
		rq.setIn(in);
		
		StringTokenizer stk =  new StringTokenizer(strArr[0],"\r\n");
		setRequestLine(rq, stk.nextToken());
		while(stk.hasMoreTokens()) {
			setRequestHeader(rq, stk.nextToken());
		}
		
		setSpecialHeaders(rq);
		setRequestParameters(rq);
		
		return rq;
	}

	private static void setRequestParameters(HttpRequestImpl rq) {
		if(rq.getMethod().equalsIgnoreCase("GET")) {
			setParameter(rq, rq.getQueryString());
		}else {
			if( rq.getContentType().indexOf("application/www-form-urlencoded") != -1) {
				 
			}else if( rq.getContentType().indexOf("application/json") != -1){
				
			}else if( rq.getContentType().indexOf("application/xml") != -1){
				
			}
		}
	}
	
	private static void setParameter(HttpRequestImpl rq, String paramstr) {
		if(paramstr == null || "".equals(paramstr.trim()))return;
		if(paramstr != null && !paramstr.equals("")) {
			StringTokenizer stk = new StringTokenizer(paramstr, "&");
			while(stk.hasMoreTokens()) {
				String kvStr = stk.nextToken();
				String[] kvArr = kvStr.split("=");
				if(kvArr.length != 2) {
					//这参数有问题
					throw new RuntimeException();
				}
				else {
					try {
						kvArr[0] = URLDecoder.decode(kvArr[0],AppConfig.URIEncoding);
						kvArr[1] = URLDecoder.decode(kvArr[1],AppConfig.URIEncoding);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					List<String> plist = rq.getParametersMap().get(kvArr[0]);
					if(plist == null) {
						plist = new ArrayList<>();
					}
					plist.add(kvArr[1]);
					rq.getParametersMap().put(kvArr[0], plist);
				}
			}
		}
	}

	private static void setSpecialHeaders(HttpRequestImpl rq) {
		rq.setContentType(rq.getHeadersMap().get("Content-Type"));
		String line = rq.getHeadersMap().get("Content-Length");
		if( line != null ) {
			rq.setContentLength(Integer.parseInt(line));
		}
		String cookieStr = rq.getHeadersMap().get("Cookie");
		if(cookieStr != null && !cookieStr.equals("")) {
			for(String ckStr : cookieStr.split("##")) {
				Cookie ck = new Cookie();
				for(String kvStr : ckStr.split(";")) {
					String[] kv = kvStr.split("=");
					if(kv.length == 2) {
						String key = kv[0].trim();
						String value = kv[1].trim();
						switch(key) {
							case "Path":
								ck.setPath(value);
								break;
							case "Expires":
								ck.setExpires(value);
								break;
							default:
								ck.setName(key);
								ck.setValue(value);
								break;
						}
					}
					rq.addCookie(ck);
				}
			}
		}
	}

	private static void setRequestHeader(HttpRequestImpl rq, String headerStr) {
		int index = headerStr.indexOf(":");
		String key = headerStr.substring(0, index).trim();
		String value = headerStr.substring(index+1, headerStr.length()).trim();
		String old = rq.getHeadersMap().get(key);
		if(old != null && !old.equals("")) {
			value = old+"##"+value;
		}
		rq.getHeadersMap().put(key, value);
	}

	private static void setRequestLine(HttpRequestImpl rq, String line) {
		StringTokenizer stk = new StringTokenizer(line);
		rq.setMethod(stk.nextToken());
		String uri = stk.nextToken();
		rq.setUri(uri);
		String[] rs = uri.split("\\?");
		rq.setRequestUri(rs[0]);
		rq.setQueryString(rs.length > 1 ? rs[1] : null);
		String path = rs[0];
		if (path.startsWith(AppConfig.getAppName())) {
			path = path.replaceFirst(AppConfig.getAppName(), "");
		}
		rq.setServletPath(path);
	}
	
}
