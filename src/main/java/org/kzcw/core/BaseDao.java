package org.kzcw.core;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDao<T extends Serializable> {
    //数据库操作基类
	
	@Autowired
	private SessionFactory sessionFactory;
	// 当前泛型类
	@SuppressWarnings("rawtypes")
	private Class entityClass;
	
	
	@SuppressWarnings({ "unchecked" })
	public BaseDao() {
		// 获取当前泛型类
		setEntityClass((Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0]);
	}

	@SuppressWarnings("rawtypes")
	public Class getEntityClass() {
		return entityClass;
	}

	@SuppressWarnings("rawtypes")
	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	public Session getSession() {
		return sessionFactory.openSession();
	}

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<T> list() {
		//获取列表
		List<T> total = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(getEntityClass());
			total=criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return total;
	}
	
	public void save(T t) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(t);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void save(List<T> t) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			for(int i=0;i<t.size();i++) {
				session.save(t);
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void update(T t) {
		//修改实例
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(t);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new HibernateException(e);
		} finally {
			session.close();
		}
	}
	
	public void delete(T t) {
		//删除实例
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.delete(t);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new HibernateException(e);
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findEntryByExecSQL(String sql) {
		//根据sql语句访问实体类列表
		List<T> list = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			list = session
					.createSQLQuery(sql)
					.setResultTransformer(
							Transformers.aliasToBean(getEntityClass())).list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> findMapByExecSQL(String sql) {
		//根据sql语句获取MAP结果
		List<Map> list = null;
		Session session = null;

		try {
			session = sessionFactory.openSession();
			list = session
					.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}
	
	public boolean ExecSQL(String sql) {
		//执行SQL语句
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.createSQLQuery(sql).executeUpdate();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findListByProperty(String name,Object value) {
		//根据属性查找记录集合
		List<T> total = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(getEntityClass());
			criteria.add(Restrictions.eq(name, value));
			total=criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return total;
	}
	
	@SuppressWarnings("unchecked")
	public T findUniqByProperty(String name,Object value) {
		//根据属性查找一条记录
		Session session = null;
		T result=null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(getEntityClass());
			criteria.add(Restrictions.eq(name, value));
			result=(T) criteria.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}
	
	public Session OpenSession() {
		//获取session对象
		return sessionFactory.openSession();
	}
}
