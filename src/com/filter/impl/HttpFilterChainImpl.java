package com.filter.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

import com.filter.HttpFilter;
import com.filter.HttpFilterChain;
import com.request.HttpRequest;
import com.response.HttpResponse;

public class HttpFilterChainImpl implements HttpFilterChain {
	
	List<HttpFilter> filterList = new ArrayList<>();
	Iterator<HttpFilter> it;
	BiConsumer<HttpRequest, HttpResponse> func;
	
	public HttpFilterChainImpl(BiConsumer<HttpRequest, HttpResponse> func) {
		this.func = func;
	}
	
	public void addFilter(HttpFilter filter) {
		filterList.add(filter);
	}
	
	public void setFilterList(List<HttpFilter> filterList) {
		this.filterList = filterList;
		it = filterList.iterator();
	}

	@Override
	public void doFilter(HttpRequest rq, HttpResponse rp) {
		if(it != null && it.hasNext()) {
			HttpFilter filter = it.next();
			filter.doFilter(rq, rp, this);
		}
		else {
			if(func != null)
				func.accept(rq, rp);
		}
	}

}
