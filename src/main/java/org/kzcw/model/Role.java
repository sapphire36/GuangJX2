package org.kzcw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.kzcw.core.General;

@Entity
@Table(name="t_role")
public class Role extends General{
	//角色管理表
	
	private static final long serialVersionUID = 8724155022862447147L;
	private String NAME; //角色名称
	private String RIGHTS;//模块权限  1,2,3,4,5,6 代表可以使用1,2,3,4,5,6模块
	
	@Column(nullable = false,length=100)
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	
	@Column(nullable = false,length=100)
	public String getRIGHTS() {
		return RIGHTS;
	}
	public void setRIGHTS(String rIGHT) {
		RIGHTS = rIGHT;
	}
}
