package org.kzcw.dao;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.kzcw.core.BaseDao;
import org.kzcw.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("userDao")
@Component
public class UserDao extends BaseDao<User>{
	
	@SuppressWarnings("unchecked")
	public User getUser(String uname, String passwd, long type) {
		List<User> total;
		User ret=null; //返回值
		Session session = null;
		try {
			session = OpenSession();
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(
						Restrictions.and( //AND
							Restrictions.eq("USERNAME",uname),//名字等于uname
							Restrictions.eq("PASSWD",passwd),
							Restrictions.eq("ROLEID",type)
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

