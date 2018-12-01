package org.kzcw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.kzcw.core.General;

@Entity
@Table(name="t_operatehistory")
public class Operatehistory extends General{
	//操作历史表
	
	private static final long serialVersionUID = -3254940818328212382L;

	private long BOXID;//光交箱体编号
	private long ORGANIZATIONID;//施工单位编号
	private String BEFOR_MAINTAIN; //维修前照片
	private String AFTER_MAINTAIN; //维修后照片
	private String FIN_MAINTAIN; //关箱后照片
	private String AREANAME;//区域
	private int SCORE;//评分
	
	
	@Column(nullable = false,length=20)
	public long getBOXID() {
		return BOXID;
	}
	public void setBOXID(long bOXID) {
		BOXID = bOXID;
	}
	
	@Column(nullable = false,length=20)
	public long getORGANIZATIONID() {
		return ORGANIZATIONID;
	}
	public void setORGANIZATIONID(long oRGANIZATIONID) {
		ORGANIZATIONID = oRGANIZATIONID;
	}
	
	@Column(nullable = true)
	public String getBEFOR_MAINTAIN() {
		return BEFOR_MAINTAIN;
	}
	public void setBEFOR_MAINTAIN(String bEFOR_MAINTAIN) {
		BEFOR_MAINTAIN = bEFOR_MAINTAIN;
	}
	
	@Column(nullable = true)
	public String getAFTER_MAINTAIN() {
		return AFTER_MAINTAIN;
	}
	public void setAFTER_MAINTAIN(String aFTER_MAINTAIN) {
		AFTER_MAINTAIN = aFTER_MAINTAIN;
	}
	
	@Column(nullable = true)
	public String getFIN_MAINTAIN() {
		return FIN_MAINTAIN;
	}
	public void setFIN_MAINTAIN(String fIN_MAINTAIN) {
		FIN_MAINTAIN = fIN_MAINTAIN;
	}
	
	@Column(nullable = true)
	public int getSCORE() {
		return SCORE;
	}
	public void setSCORE(int sCORE) {
		SCORE = sCORE;
	}
	
	@Column(nullable = true)
	public String getAREANAME() {
		return AREANAME;
	}
	public void setAREANAME(String aREANAME) {
		AREANAME = aREANAME;
	}
}

