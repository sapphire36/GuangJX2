package org.kzcw.service;

import java.util.ArrayList;
import java.util.List;

import org.kzcw.core.BaseServiceImpl;
import org.kzcw.dao.RoleDao;
import org.kzcw.model.Module;
import org.kzcw.model.Role;
import org.kzcw.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("roleService")
@Component
public class RoleService extends BaseServiceImpl<Role>{
	
	@Autowired
	private ModuleService moduleService;
	
	private RoleDao dao;
	
	
	@Autowired
	@Qualifier("roleDao")
	public void setroleDao(RoleDao dao) {
		this.dao = dao;
	}
	
	public List<Module> getModules(long roleid){
		List<Module> result = new ArrayList<Module>();
		Role role = findById(roleid);
		String rights = role.getRIGHTS();
		if (rights == null) {
			return null;
		}
		if (!rights.equals("")) {
			String[] arr = rights.split(",");// 分割获取模块列表
			List<Module> plist = moduleService.getParentList();
			for (Module parent : plist) {
				if (IsInRight(parent.getCODE(), arr)) {
					result.add(parent);
					List<Module> clist = moduleService.getChildren(parent.getCODE());
					for(Module chirld : clist) {
						if (IsInRight(chirld.getCODE(), arr)) {// 如果存在,则添加到返回列表中
							result.add(chirld);
						}
					}
				}
			}
		}
		return result;
	}
	
	private boolean IsInRight(long id, String[] arr) {
		// 判断是否在列表中
		String code = String.valueOf(id);
		for (int i = 0; i < arr.length; i++) {
			if (code.equals(arr[i])) {
				return true;
			}
		}
		return false;
	}
}
