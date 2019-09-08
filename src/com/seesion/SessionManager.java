package com.seesion;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
	
	Map<String, Session> sessionMap = new HashMap<String, Session>();
	
	public Session getSession(String key) {
		return sessionMap.get(key);
	}
	
	public void removeSession(String key) {
		sessionMap.remove(key);
	}
	
	public void setSession(String key, Session session) {
		sessionMap.put(key, session);
	}
	
	public void run() {
		//用于清理map
	}
}
