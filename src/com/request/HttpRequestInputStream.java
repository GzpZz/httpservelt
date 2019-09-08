package com.request;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

public class HttpRequestInputStream extends InputStream {

	InputStream in;
	LinkedList<Byte> bytes = new LinkedList<>();
	
	public HttpRequestInputStream(InputStream in) {
		this.in = in;
	}
	
	public void prepend(String str) {
		if(str != null && !str.equals("")) {
			byte[] bs = str.getBytes();
			for(byte b : bs) {
				bytes.push(b);
			}
		}
	}

	@Override
	public int read() throws IOException {
		if(bytes.size() > 0) {
			return bytes.pop();
		}
		else {
			return in.read();
		}
	}
	
}
