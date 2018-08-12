package org.kzcw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.kzcw.core.General;

@Entity
@Table(name = "t_breakhistory")
public class Breakhistory extends General {
	// 故障信息表
	private static final long serialVersionUID = -8342801746053290192L;

	private String IEME;// 设备IEME
	private String TYPE;// 故障原因
	private int ISHADND;// 是否处理 0代表未被处理,1代表已处理
	private String AREANAME;//区域

	public Breakhistory() {
		// TODO Auto-generated constructor stub
	}

	public Breakhistory(String ieme, String type) {
		// TODO Auto-generated constructor stub
		this.IEME = ieme;
		this.TYPE = type;
	}

	@Column(nullable = false, length = 50)
	public String getIEME() {
		return IEME;
	}

	public void setIEME(String iEME) {
		IEME = iEME;
	}

	@Column(nullable = false, length = 50)
	public String getTYPE() {
		return TYPE;
	}

	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}

	@Column(nullable = true,columnDefinition="INT default 0")
	public int getISHADND() {
		return ISHADND;
	}
	public void setISHADND(int iSHADND) {
		ISHADND = iSHADND;
	}
	
	@Column(nullable = true)
	public String getAREANAME() {
		return AREANAME;
	}
	public void setAREANAME(String aREANAME) {
		AREANAME = aREANAME;
	}
}
