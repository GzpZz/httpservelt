package com.request;

import java.util.function.Consumer;

import com.response.HttpResponse;

public class RequestDispatcherImpl implements RequestDispatcher {
	
	private String type = null;
	private String path = null;
	private Consumer<String> func = null;
	
	public RequestDispatcherImpl(){ }
	
	public RequestDispatcherImpl(Consumer<String> func) {
		this.func = func;
	}
	
	public void setConsumer(Consumer<String> func) {
		this.func = func;
	}
	
	public String getDispatcherType() {
		return type;
	}

	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	private void doFunc(HttpRequest rq) {
		if(func != null) {
			rq.setAttribute(RequestDispatcher.DISPATCHER_FLAG, true);
			func.accept(path);
		}
	}

	@Override
	public void forward(HttpRequest rq, HttpResponse rp) {
		if(rp.isCommitted()) {
			throw new IllegalStateException();
		}
		rp.resetBuffer();
		this.type = "forward";
		doFunc(rq);
	}

	@Override
	public void include(HttpRequest rq, HttpResponse rp) {
		if(rp.isCommitted()) {
			throw new IllegalStateException();
		}
		this.type = "include";
		doFunc(rq);
	}

}
