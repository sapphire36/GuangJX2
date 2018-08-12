package org.kzcw.common.Iot.youren;

public class ReceiveData {

	public int messageId;
	public String topic;
	public byte[] data;
	
	public ReceiveData() {
		// TODO Auto-generated constructor stub
	}
	
	public ReceiveData(int message,String top,byte[] dd) {
		// TODO Auto-generated constructor stub
		this.messageId=message;
		this.topic=top;
		this.data=dd;
	}
}
