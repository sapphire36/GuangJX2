package org.kzcw.common.Iot.youren;
public class OperaType {
	public String EMEI;
	public int type; //1 为开锁   0为关锁
	
	public OperaType(String emei,int t) {
		// TODO Auto-generated constructor stub
		this.EMEI=emei;
		this.type=t;
	}
}
