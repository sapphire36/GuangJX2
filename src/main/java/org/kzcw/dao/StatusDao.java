package org.kzcw.dao;


import org.kzcw.model.Status;

import java.util.List;
import org.kzcw.core.BaseDao;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("StatusDao")
@Component
public class StatusDao extends BaseDao<Status>{

	public List<Status> getStatusByIEME(String ieme) {
		return findListByProperty("IEME",ieme);
	}
	
	public List<Status> getStatusByAreaName(String areaname) {
		return findListByProperty("AREANAME",areaname);
	}
}
