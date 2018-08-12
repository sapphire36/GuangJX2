package org.kzcw.service;

import org.kzcw.core.BaseServiceImpl;
import org.kzcw.dao.ModuleDao;
import org.kzcw.model.Module;
import org.kzcw.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("moduleService")
@Component
public class ModuleService extends BaseServiceImpl<Module>{
	
	private ModuleDao dao;

	@Autowired
	@Qualifier("moduleDao")
	public void setDao(ModuleDao dao) {
		this.dao = dao;
	}
	
	public boolean deleteAllItem() {
		//清空数据表
		return dao.deleteAllItem();
	}
}
