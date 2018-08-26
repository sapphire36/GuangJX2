package org.kzcw.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.kzcw.core.BaseDao;
import org.kzcw.model.Lightbox;
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
	
	public List<Module> getParentList() {
		List<Module> total = null;
		Session session = null;
		try {
			session = OpenSession();
			Criteria criteria = session.createCriteria(Module.class);
			criteria.add(Restrictions.eq("ISLEAF",0));
			total=criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return total;
	}
	
	public List<Module> getChildren(int parentID) {
		List<Module> total = null;
		Session session = null;
		try {
			session = OpenSession();
			Criteria criteria = session.createCriteria(Module.class);
			criteria.add(Restrictions.and(
					    Restrictions.eq("ISLEAF",1),
					    Restrictions.eq("PARRENTCODE",parentID)
					));
			total=criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return total;
	}
}
