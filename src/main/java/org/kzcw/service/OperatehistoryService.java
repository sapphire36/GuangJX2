package org.kzcw.service;

import org.kzcw.core.BaseServiceImpl;
import org.kzcw.dao.OperatehistoryDao;
import org.kzcw.model.Operatehistory;
import org.kzcw.service.LightboxService;
import org.kzcw.service.OperatehistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("operatehistoryService")
@Component
public class OperatehistoryService extends BaseServiceImpl<Operatehistory>{
	
	@Autowired 
	private LightboxService operLogService;
	
	private OperatehistoryDao dao;
	
	
	@Autowired
	@Qualifier("operatehistoryDao")
	public void setoperatehistoryDao(OperatehistoryDao dao) {
		this.dao = dao;
	}
	
	public boolean deleteAllItemByAreaName(String areaName) {
		//清空数据表
		String sql=String.format("delete from t_operatehistory where AREANAME='%s'", areaName);
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
