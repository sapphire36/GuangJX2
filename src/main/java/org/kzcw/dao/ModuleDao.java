package org.kzcw.dao;

import org.kzcw.core.BaseDao;
import org.kzcw.model.Module;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("moduleDao")
@Component
public class ModuleDao extends BaseDao<Module>{
	
	public boolean deleteAllItem() {
		//清空数据表
		String sql="delete from t_module";
		boolean result=false;
		try {
			result=ExecSQL(sql);
		} catch (Exception e) {
			result=false;
		}
		return result;
	}
}
