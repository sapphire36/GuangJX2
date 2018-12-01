package org.kzcw.service;

import org.kzcw.core.BaseServiceImpl;
import org.kzcw.dao.SystemlogsDao;
import org.kzcw.model.Systemlogs;
import org.kzcw.service.SystemlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("systemlogsService")
@Component
public class SystemlogsService extends BaseServiceImpl<Systemlogs>{
	
	
	private SystemlogsDao dao;
	
	
	@Autowired
	@Qualifier("systemlogsDao")
	public void setsystemlogsDao(SystemlogsDao dao) {
		this.dao = dao;
	}
	
	public boolean deleteAll() {
		//清空数据表
		String sql="delete from t_systemlog";
		boolean result=false;
		try {
			result=dao.ExecSQL(sql);
		} catch (Exception e) {
			// TODO: handle exception
			result=false;
		}
		return result;
	}

}
