package org.kzcw.dao;

import java.util.List;

import org.kzcw.core.BaseDao;
import org.kzcw.model.Breakhistory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("breakhistoryDao")
@Component
public class BreakhistoryDao extends BaseDao<Breakhistory>{
	
	public List<Breakhistory> getBreakhistoryByAreaName(String areaname) {
		return findListByProperty("AREANAME",areaname);
	}
}
