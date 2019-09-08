package com;

import java.io.IOException;

import com.server.HttpServer;

public class StartUp {

	public static void main(String[] args) throws IOException {
		new HttpServer().run();
	}

}