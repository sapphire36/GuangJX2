package org.kzcw.common;

import org.kzcw.model.User;

public class UserSession {
	private User user;// 用户
	private String jsessionId;//用户登录sessionID
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getJsessionId() {
		return jsessionId;
	}

	public void setJsessionId(String jsessionId) {
		this.jsessionId = jsessionId;
	}
}
