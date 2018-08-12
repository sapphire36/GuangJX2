package org.kzcw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.kzcw.core.General;
@Entity
@Table(name="t_area")
public class Area extends General{
    
	//有人网络连接信息
	private static final long serialVersionUID = -188992185943354063L;

	private String AREANAME;//区域名
	private String LOGINNAME; //该区域有人网登录名
	private String PASSWD;//该区域有人网登录密码
	private int ISSUBSCRIBE;//是否订阅 默认是 0 未订阅
	
	
	@Column(nullable = false,length=50)
	public String getAREANAME() {
		return AREANAME;
	}
	public void setAREANAME(String aREANAME) {
		AREANAME = aREANAME;
	}
	
	@Column(nullable = false,length=50)
	public String getLOGINNAME() {
		return LOGINNAME;
	}
	public void setLOGINNAME(String lOGINNAME) {
		LOGINNAME = lOGINNAME;
	}
	
	@Column(nullable = false,length=50)
	public String getPASSWD() {
		return PASSWD;
	}
	public void setPASSWD(String pASSWD) {
		PASSWD = pASSWD;
	}
	
	@Column(nullable = true,columnDefinition="INT default 0")
	public int getISSUBSCRIBE() {
		return ISSUBSCRIBE;
	}
	public void setISSUBSCRIBE(int iSSUBSCRIBE) {
		ISSUBSCRIBE = iSSUBSCRIBE;
	}
}
