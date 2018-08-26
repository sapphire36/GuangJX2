package org.kzcw.common.Iot.utils;

import java.util.Date;

public class BaseMessage {

	public String USERNAME;// 用户名字
	public String Location;// 开锁位置
	public String time;// 开锁时间
	public String EMEI;
	public boolean isOpen = true; // true为开锁 false为关锁
	public boolean IsReady = false;// 是否准备发送

	public BaseMessage(String uid, String emei, boolean type) {
		// 基本控制信息
		// TODO Auto-generated constructor stub
		this.USERNAME = uid;
		this.EMEI = emei;
		this.isOpen = type;
		this.time = new Date().toString();
	}

	public BaseMessage() {
		// TODO Auto-generated constructor stub
	}
}
