package com.response;

import java.io.IOException;
import java.io.OutputStream;

public class HttpResponseOutputStream extends OutputStream {
	
	int count = 0;
	int bufferSize = 4*1024;
	byte[] buffer;
	OutputStream out;
	HttpResponseImpl rp;
	
	public HttpResponseOutputStream(HttpResponseImpl rp, OutputStream out) {
		this.rp = rp;
		this.out = out;
		buffer = new byte[bufferSize];
	}

	@Override
	public void write(int b) throws IOException {
		buffer[count++] = (byte)b;
		if(count == bufferSize) {
			flush();
		}
	}
	
	@Override
	public void flush() throws IOException {
		if(!rp.isCommitted()) {
			out.write(rp.getHeader().getBytes());
			rp.setCommited(true);
		}
		out.write(buffer, 0, count);
		count = 0;
	}
	
	public void setBufferSize(int size) {
		bufferSize = size;
		try {
			flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		buffer = new byte[bufferSize];
	}
	
	public void clearBuffer() {
		count = 0;
	}

}
