package org.kzcw.service;

import java.util.List;

import org.kzcw.core.BaseServiceImpl;
import org.kzcw.dao.BreakhistoryDao;
import org.kzcw.model.Breakhistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("breakhistoryService")
@Component
public class BreakhistoryService extends BaseServiceImpl<Breakhistory>{

	@Autowired 
	private BreakhistoryDao dao;
	
	
	@Autowired
	@Qualifier("breakhistoryDao")
	public void setbreakhistoryDao(BreakhistoryDao dao) {
		this.dao = dao;
	}
	
	public List<Breakhistory> getBreakhistoryByAreaName(String areaname) {
		return dao.getBreakhistoryByAreaName(areaname);
	}
}
