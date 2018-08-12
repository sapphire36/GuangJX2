package org.kzcw.dao;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.kzcw.core.BaseDao;
import org.kzcw.model.Area;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("AreaDao")
@Component
public class AreaDao extends BaseDao<Area>{

	@SuppressWarnings("unchecked")
	public Area getAreaByName(String areaname) {
		List<Area> total;
		Area ret=null; //返回值
		Session session = null;
		try {
			session = OpenSession();
			Criteria criteria = session.createCriteria(Area.class);
			criteria.add(Restrictions.eq("AREANAME",areaname));
			total=criteria.list();
			if(total!=null) {
				if(total.size()>0) {
					ret = total.get(0);
				}
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return ret;	
	}
}
