package org.kzcw.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.kzcw.core.BaseDao;
import org.kzcw.model.Organization;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("organizationDao")
@Component
public class OrganizationDao extends BaseDao<Organization>{

	@SuppressWarnings("unchecked")
	public List<Organization> getPeopleByOrganizationID(long id){
		List<Organization> total = null;
		Session session = null;
		try {
			session = OpenSession();
			Criteria criteria = session.createCriteria(Organization.class);
			criteria.add(Restrictions.eq("BELONGTO",id));
			total=criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return total;	
	}
	
	@SuppressWarnings("unchecked")
	public List<Organization> getOranizations(){
		List<Organization> total = null;
		Session session = null;
		try {
			session = OpenSession();
			Criteria criteria = session.createCriteria(Organization.class);
			criteria.add(Restrictions.eq("TYPE",1)); //1时为企业
			total=criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return total;	
	}
	
	@SuppressWarnings("unchecked")
	public Organization getOrganization(String uname, String passwd, long type) {
		List<Organization> total;
		Organization ret=null; //返回值
		Session session = null;
		try {
			session = OpenSession();
			Criteria criteria = session.createCriteria(Organization.class);
			criteria.add(
						Restrictions.and( //AND
							Restrictions.eq("LOGINNAME",uname),//名字等于uname
							Restrictions.eq("PASSWD",passwd),
							Restrictions.eq("UTYPE",type)
						)
					);
			total=criteria.list();
			if(total!=null) {
				//如果找到该用户
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
