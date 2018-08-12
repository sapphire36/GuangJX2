package org.kzcw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.kzcw.core.General;

@Entity
@Table(name="t_organization")
public class Organization extends General{
	//施工单位信息表
	private static final long serialVersionUID = -165494081832825L;

	private String NAME;//单位名称/或个人名
	private String ADDRESS;//地址
	private String TEL;//电话
	private int DEPOSIT;//押金
	private int TYPE;//1代表企业 0代表个人
	private long BELONGTO;//如果是企业 则BELONGTO的值代表其归属的企业
	private String LOGINNAME;
	private String PASSWD;//登录小程序的密码
	private int UTYPE;//类别:1代表安装人员,2代表施工人员
	private int STATUS; //1代表可用,0代表禁用
	
	@Column(nullable = false,length=50)
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	
	@Column(nullable = true,length=50)
	public String getADDRESS() {
		return ADDRESS;
	}
	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}
	
	@Column(nullable = true,length=20)
	public String getTEL() {
		return TEL;
	}
	public void setTEL(String tEL) {
		TEL = tEL;
	}
	
	@Column(nullable = true,length=20)
	public int getDEPOSIT() {
		return DEPOSIT;
	}
	public void setDEPOSIT(int dEPOSIT) {
		DEPOSIT = dEPOSIT;
	}
	
	@Column(nullable = true,columnDefinition="INT default 0")
	public int getTYPE() {
		return TYPE;
	}
	public void setTYPE(int tYPE) {
		TYPE = tYPE;
	}
	
	@Column(nullable = true)
	public long getBELONGTO() {
		return BELONGTO;
	}
	public void setBELONGTO(long bELONGTO) {
		BELONGTO = bELONGTO;
	}
	
	@Column(nullable = true)
	public String getLOGINNAME() {
		return LOGINNAME;
	}
	public void setLOGINNAME(String lOGINNAME) {
		LOGINNAME = lOGINNAME;
	}
	
	@Column(nullable = true)
	public String getPASSWD() {
		return PASSWD;
	}
	public void setPASSWD(String pASSWD) {
		PASSWD = pASSWD;
	}
	
	@Column(nullable = true,columnDefinition="INT default 1")
	public int getUTYPE() {
		return UTYPE;
	}
	public void setUTYPE(int uTYPE) {
		UTYPE = uTYPE;
	}
	
	@Column(nullable = true,columnDefinition="INT default 1")
	public int getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}
}

