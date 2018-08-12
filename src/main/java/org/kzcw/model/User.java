package org.kzcw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.kzcw.core.General;

@Entity
@Table(name="t_user")
public class User extends General{
	//用户信息表
	
	private static final long serialVersionUID = 3838171557701919198L;
	
	private String USERNAME;//用户名
	private String PASSWD;//密码
	private long ROLEID;//角色ID
	private int STATUS;//状态:1表示正常,0表示禁用
	private String AREANAME;//有人网络区域名字
	
	@Column(nullable = false,length=50)
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	
	@Column(nullable = false,length=50)
	public String getPASSWD() {
		return PASSWD;
	}
	
	public void setPASSWD(String pASSWD) {
		PASSWD = pASSWD;
	}
	
	@Column(nullable = false,length=20)
	public long getROLEID() {
		return ROLEID;
	}
	public void setROLEID(long rOLEID) {
		ROLEID = rOLEID;
	}
	
	@Column(nullable = true,columnDefinition="INT default 1")
	public int getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}
	
	@Column(nullable = true,length=50)
	public String getAREANAME() {
		return AREANAME;
	}
	public void setAREANAME(String aREANAME) {
		AREANAME = aREANAME;
	}
}

