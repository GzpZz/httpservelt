package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.config.WebXMLConfig;
import com.request.HttpRequest;
import com.request.HttpRequestFactory;
import com.response.HttpResponse;
import com.response.HttpResponseFactory;

public class HttpServer {
	
	@SuppressWarnings("resource")
	public void run() throws IOException {
		ServerSocket ss = new ServerSocket(80);
		initAppConfig();
		
		ThreadPoolExecutor tpe = new ThreadPoolExecutor(4, 8, 10000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
		
		while(true) {
			Socket client = ss.accept();
			tpe.execute(()->{
				try {
					HttpRequest rq = HttpRequestFactory.getHttpRequest(client.getInputStream());
					HttpResponse rp = HttpResponseFactory.getHttpResponse(client.getOutputStream());
					new HttpService().execute(rq, rp);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						client.shutdownOutput();
						client.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	private void initAppConfig() {
		initWebXML();
		initConfig();
	}

	private void initConfig() {
		 
	}

	private void initWebXML() {
		WebXMLConfig.init();
	}
	
}
