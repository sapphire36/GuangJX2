package org.kzcw.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.kzcw.core.General;

@Entity
@Table(name="t_lightbox")
public class Lightbox extends General{
    //光交箱信息表
	private static final long serialVersionUID = -4654940818328212382L;
	private String NAME;//箱体名称（客户自定义箱体名）
	private String LOCATION; //安装位置 格式如:75.15,562.2
	private String IEME;//锁编号
	private String PEOPLE;//安装人员
	private String PHOTO; //安装时照片
	private String AREANAME;//所在区域名
	private int ISONLINE;//1 表示在线   0表示下线
	private int UNLOCKSTATUS;//关锁状态
	private int DOORSTATUS;//门状态
	private int CONSTRUCTSTATUS;//施工状态
	private int ISREGIST;//是否注册:1表示已经注册   0表示未注册

	@Column(nullable = false,length=50)
	public String getAREANAME() {
		// TODO Auto-generated method stub
		return AREANAME;
	}
	public void setAREANAME(String aREANAME) {
		AREANAME=aREANAME;
	}
	
	@Column(nullable = true,length=50)
	public String getPEOPLE() {
		return PEOPLE;
	}
	public void setPEOPLE(String pEOPLE) {
		PEOPLE = pEOPLE;
	}
	
	@Column(nullable = true,length=50)
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}	
	
	@Column(nullable = true,length=50)
	public String getLOCATION() {
		return LOCATION;
	}
	public void setLOCATION(String lOCATION) {
		LOCATION = lOCATION;
	}
	
	@Column(nullable = false,length=50)
	public String getIEME() {
		return IEME;
	}
	public void setIEME(String iEME) {
		IEME = iEME;
	}
	
	@Column(nullable = true)
	public String getString() {
		return PHOTO;
	}
	public void setString(String pHOTO) {
		PHOTO = pHOTO;
	}
	
	@Column(nullable = true,columnDefinition="INT default 0")
	public int getISREGIST() {
		return ISREGIST;
	}
	public void setISREGIST(int iSREGIST) {
		ISREGIST = iSREGIST;
	}
	
	@Column(nullable = true,columnDefinition="INT default 0")
	public int getISONLINE() {
		return ISONLINE;
	}
	public void setISONLINE(int iSONLINE) {
		ISONLINE = iSONLINE;
	}
	
	@Column(nullable = true,columnDefinition="INT default 0")
	public int getUNLOCKSTATUS() {
		return UNLOCKSTATUS;
	}
	public void setUNLOCKSTATUS(int uNLOCKSTATUS) {
		UNLOCKSTATUS = uNLOCKSTATUS;
	}
	
	@Column(nullable = true,columnDefinition="INT default 0")
	public int getDOORSTATUS() {
		return DOORSTATUS;
	}
	public void setDOORSTATUS(int dOORSTATUS) {
		DOORSTATUS = dOORSTATUS;
	}
	
	@Column(nullable = true,columnDefinition="INT default 0")
	public int getCONSTRUCTSTATUS() {
		return CONSTRUCTSTATUS;
	}
	public void setCONSTRUCTSTATUS(int cONSTRUCTSTATUS) {
		CONSTRUCTSTATUS = cONSTRUCTSTATUS;
	}
}

