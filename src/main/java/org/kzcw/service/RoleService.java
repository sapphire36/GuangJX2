package org.kzcw.service;

import org.kzcw.core.BaseServiceImpl;
import org.kzcw.dao.RoleDao;
import org.kzcw.model.Role;
import org.kzcw.service.LightboxService;
import org.kzcw.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("roleService")
@Component
public class RoleService extends BaseServiceImpl<Role>{
	
	
	private RoleDao dao;
	
	
	@Autowired
	@Qualifier("roleDao")
	public void setroleDao(RoleDao dao) {
		this.dao = dao;
	}
}
