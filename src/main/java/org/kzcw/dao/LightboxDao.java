package org.kzcw.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.kzcw.core.BaseDao;
import org.kzcw.model.Lightbox;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("lightboxDao")
@Component
public class LightboxDao extends BaseDao<Lightbox> {
	
	@SuppressWarnings("unchecked")
	public List<Lightbox> findByAreaName(String area) {
		List<Lightbox> total = null;
		Session session = null;
		try {
			session = OpenSession();
			Criteria criteria = session.createCriteria(Lightbox.class);
       		//选择t_lightbox表中,AREANAME为area的值
			criteria.add(Restrictions.eq("AREANAME",area));
			total=criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return total;
	}
	
	public Lightbox findByIMEI(String imei) {
		List<Lightbox> total = null;
		Session session = null;
		try {
			session = OpenSession();
			Criteria criteria = session.createCriteria(Lightbox.class);
       		//选择t_lightbox表中,AREANAME为area的值
			criteria.add(Restrictions.eq("IEME",imei));
			total=criteria.list();
			if(total!=null) {
				if(total.size()>0) {
					return total.get(0);
				}
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}
}