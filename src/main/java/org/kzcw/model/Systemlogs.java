package org.kzcw.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.kzcw.core.General;
@Entity
@Table(name="t_systemlog")
public class Systemlogs extends General{
	//系统日志
	
	private static final long serialVersionUID = -5847204683834354572L;
	private String CONTENT;//日志类型
	private String TYPE;//1:代表消息  2:代表警告  3:代表故障
	private String AREANAME;//区域
	
	public Systemlogs(String top,String t) {
		// TODO Auto-generated constructor stub
		this.CONTENT=top;
		this.TYPE=t;
	}
	public Systemlogs() {
		// TODO Auto-generated constructor stub
	}
	
	@Column(nullable = false,length=50)
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	
	@Column(nullable = false)
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	
	@Column(nullable = true)
	public String getAREANAME() {
		return AREANAME;
	}
	public void setAREANAME(String aREANAME) {
		AREANAME = aREANAME;
	}
}
