package com.config;

public class AppConfig {
	private static String appName="/ROOT";
	private static String res_path="./WebContent";
	public static String URIEncoding="UTF-8";
	public static String getAppName() {
		return appName;
	}
	public static void setAppName(String appName) {
		AppConfig.appName = appName;
	}
	public static String getRes_path() {
		return res_path;
	}
	public static void setRes_path(String res_path) {
		AppConfig.res_path = res_path;
	}
}
