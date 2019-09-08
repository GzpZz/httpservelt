package com.server;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.config.AppConfig;
import com.filter.FilterContaioner;
import com.filter.HttpFilter;
import com.filter.impl.HttpFilterChainImpl;
import com.request.HttpRequest;
import com.request.RequestDispatcher;
import com.response.HttpResponse;
import com.response.HttpResponseImpl;
import com.servlet.HttpServlet;
import com.servlet.ServletContainer;
import com.utils.FileUtils;
import com.utils.MIMEUtils;

public class HttpService {

	public void execute(HttpRequest rq, HttpResponse rp) {
		
		List<HttpFilter> filters = FilterContaioner.getFiltersByURI(rq.getServletPath());

		HttpFilterChainImpl filterChain = new HttpFilterChainImpl(this::execute0);
		filterChain.setFilterList(filters);
		filterChain.doFilter(rq, rp);

		rp.flushBuffer();
	}
	
	private void execute0(HttpRequest rq, HttpResponse rp) {
		HttpServlet servlet = ServletContainer.getServletByURI(rq.getServletPath());
		if (servlet == null) {
			findResource(rq, rp);
		}else {
			servlet.service(rq, rp);
			Boolean flag = (Boolean) rq.getAttribute(RequestDispatcher.DISPATCHER_FLAG);
			if(flag != null && flag) {
				rq.removeAttribute(RequestDispatcher.DISPATCHER_FLAG);
				execute0(rq, rp);
			}
		}
	}
	
	public void findResource(HttpRequest rq, HttpResponse rp) {
		try {
			String uri = rq.getServletPath();
			File f = new File(AppConfig.getRes_path() + uri);
			String contentType = MIMEUtils.getMIME(uri);
			if(f.exists()) {
				rp.setContentLength(f.length());
				rp.setContentType(contentType);
				FileUtils.copyFile(f, rp.getOutputStream());
			}else {//404
				((HttpResponseImpl)rp).setStatus("404 Not Found");
				byte [] bs = "<h1>亲，你要找的资源不存在哦~</h1>".getBytes("UTF-8");
				rp.setContentLength(bs.length);
				rp.setContentType("text/html;charset=UTF-8");
				rp.getOutputStream().write(bs);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
