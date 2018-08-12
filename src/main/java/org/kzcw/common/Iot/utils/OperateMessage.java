package org.kzcw.common.Iot.utils;

public class OperateMessage  extends BaseMessage{
    //开锁列表消息
	public OperateMessage(String uid,String emei,boolean type) {
		// TODO Auto-generated constructor stub
		super(uid, emei, type);
	}
	
	public void setReady() {
		IsReady=true;
	}
}
