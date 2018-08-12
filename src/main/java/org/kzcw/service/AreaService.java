package org.kzcw.service;
import org.kzcw.core.BaseServiceImpl;
import org.kzcw.dao.AreaDao;
import org.kzcw.model.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component("areaService")
public class AreaService extends BaseServiceImpl<Area>{
	
	private AreaDao dao;
	
	@Autowired
	@Qualifier("AreaDao")
	public void setareaDao(AreaDao dao) {
		this.dao = dao;
	}
	
	public Area getAreaByName(String areaname) {
		return dao.getAreaByName(areaname);
	}
}
