package org.kzcw.service;

import java.util.ArrayList;
import java.util.List;
import org.kzcw.core.BaseServiceImpl;
import org.kzcw.dao.OrganizationDao;
import org.kzcw.model.Organization;
import org.kzcw.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("organizationService")
@Component
public class OrganizationService extends BaseServiceImpl<Organization> {
	
	private OrganizationDao dao;
	
	@Autowired
	@Qualifier("organizationDao")
	public void setorganizationDao(OrganizationDao dao) {
		this.dao = dao;
	}
	
	public List<Organization> getPeopleByOrganizationID(long id){
		//获取单位底下的所有该单位的人员
		return dao.getPeopleByOrganizationID(id);
	}
	
	public List<Organization> getOranizations(){
		//获取单位底下的所有该单位的人员
		return dao.getOranizations();
	}
	
	public int doLogin(String uname, String passwd, long type) {
		// 返回值 0:用户名或密码错误,1:登录成功 2:用户被禁用
		Organization organization = dao.getOrganization(uname, passwd, type);
		if (organization != null) {
			if (organization.getSTATUS() == 0) {
				return 2;
			} else {
				return 1;
			}
		} else {
			return 0;
		}
	}
}
