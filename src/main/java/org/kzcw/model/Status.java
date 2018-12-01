package org.kzcw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.kzcw.core.General;

@Entity
@Table(name="t_status")
public class Status extends General{

	private static final long serialVersionUID = -4654940818328212372L;
	private String IEME;//IEME
	private String VOLTAGE;//电池电压
	private String TEMPERATURE;//机箱温度
	private int LOCKSTATUS;//开锁状态
	private int UNLOCKSTATUS;//关锁状态
	private int DOORSTATUS;//门状态
	private String AREANAME;//区域


	@Column(nullable = false,length=50)
	public String getIEME() {
		return IEME;
	}
	public void setIEME(String iEME) {
		IEME = iEME;
	}
	@Column(nullable = true,length=50)
	public String getVOLTAGE() {
		return VOLTAGE;
	}
	public void setVOLTAGE(String vOLTAGE) {
		VOLTAGE = vOLTAGE;
	}
	
	@Column(nullable = true,length=50)
	public String getTEMPERATURE() {
		return TEMPERATURE;
	}
	public void setTEMPERATURE(String tEMPERATURE) {
		TEMPERATURE = tEMPERATURE;
	}
	
	@Column(nullable = true,columnDefinition="INT default 0")
	public int getLOCKSTATUS() {
		return LOCKSTATUS;
	}
	public void setLOCKSTATUS(int lOCKSTATUS) {
		LOCKSTATUS = lOCKSTATUS;
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
	
	@Column(nullable = true)
	public String getAREANAME() {
		return AREANAME;
	}
	public void setAREANAME(String aREANAME) {
		AREANAME = aREANAME;
	}
}
