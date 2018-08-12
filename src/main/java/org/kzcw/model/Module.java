package org.kzcw.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.kzcw.core.General;

@Entity
@Table(name="t_module")
public class Module extends General{
    //模块表
	
	private static final long serialVersionUID = 5504339140608832060L;

	private String NAME;  //权限名称
	private int CODE;  //指定编号
	private int ISLEAF;   //是否是根目录 0根节点   1子节点
	private String URL;   //子目录对应的链接
	private String IMAGE; //图标
	private int PARRENTCODE; //上级菜单code 如果是自身CODE,则是菜单,如果是0则不是菜单.
	
	public Module(String NAME,int CODE,int ISLEAF,String URL,String IMAGE,int PARRENTCODE) {
		// TODO Auto-generated constructor stub
		this.NAME=NAME;
		this.CODE=CODE;
		this.ISLEAF=ISLEAF;
		this.URL=URL;
		this.IMAGE=IMAGE;
		this.PARRENTCODE=PARRENTCODE;
	}
	
	public Module() {
		// TODO Auto-generated constructor stub
	}
	
	@Column(nullable = false)
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	
	@Column(nullable = false)
	public int getCODE() {
		return CODE;
	}
	public void setCODE(int cODE) {
		CODE = cODE;
	}
	
	@Column(nullable = false)
	public int getISLEAF() {
		return ISLEAF;
	}
	public void setISLEAF(int iSLEAF) {
		ISLEAF = iSLEAF;
	}
	
	@Column(nullable = false)
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	
	@Column(nullable = false)
	public String getIMAGE() {
		return IMAGE;
	}
	public void setIMAGE(String iMAGE) {
		IMAGE = iMAGE;
	}

	@Column(columnDefinition="INT default 0",nullable = true)
	public int getPARRENTCODE() {
		return PARRENTCODE;
	}
	public void setPARRENTCODE(int pARRENTCODE) {
		PARRENTCODE = pARRENTCODE;
	}
}
