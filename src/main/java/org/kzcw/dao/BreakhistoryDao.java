package org.kzcw.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.kzcw.core.BaseDao;
import org.kzcw.model.Breakhistory;
import org.kzcw.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("breakhistoryDao")
@Component
public class BreakhistoryDao extends BaseDao<Breakhistory>{
	
	public List<Breakhistory> getBreakhistoryByAreaName(String areaname) {
		return findListByProperty("AREANAME",areaname);
	}
	
	public List<Breakhistory> getList(String areaname,int handled) {
		List<Breakhistory> total = null;
		Session session = null;
		try {
			session = OpenSession();
			Criteria criteria = session.createCriteria(Breakhistory.class);
			criteria.add(
						Restrictions.and(
							Restrictions.eq("AREANAME",areaname),
							Restrictions.eq("ISHADND",handled)
						)
					);
			total=criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return total;
	}
}
