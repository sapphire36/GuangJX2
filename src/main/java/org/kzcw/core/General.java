package org.kzcw.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.kzcw.core.Model;
@MappedSuperclass
public abstract class General extends Model {

	private static final long serialVersionUID = -6966387387911439572L;

	//public long id; //ID
	private long ID;//ID编号
	private String OPERATION;  //操作人员
	private Date ADDTIME;  //创建时间
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	
	@Column(nullable = true,length=50)
	public String getOPERATION() {
		return OPERATION;
	}
	public void setOPERATION(String oPERATION) {
		OPERATION = oPERATION;
	}
	
	@Column(nullable = true,length=50)
	public Date getADDTIME() {
		return ADDTIME;
	}
	public void setADDTIME(Date aDDTIME) {
		ADDTIME = aDDTIME;
	}
}
