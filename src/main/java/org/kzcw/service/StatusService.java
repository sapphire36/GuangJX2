package org.kzcw.service;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.kzcw.core.BaseServiceImpl;
import org.kzcw.dao.StatusDao;
import org.kzcw.model.Status;
import org.kzcw.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Service("StatusService")
@Component
public class StatusService extends BaseServiceImpl<Status>{

	private StatusDao dao;

	@Autowired
	@Qualifier("StatusDao")
	public void setStatusDao(StatusDao dao) {
		this.dao = dao;
	}
	
	public boolean deleteAllItem() {
		//清空数据表
		String sql="delete from t_status";
		boolean result=false;
		try {
			result=dao.ExecSQL(sql);
		} catch (Exception e) {
			// TODO: handle exception
			result=false;
		}
		return result;
	}
	
	public boolean deleteAllItemByAreaName(String areaName) {
		//清空数据表
		String sql=String.format("delete from t_status where AREANAME='%s'", areaName);
		boolean result=false;
		try {
			result=dao.ExecSQL(sql);
		} catch (Exception e) {
			// TODO: handle exception
			result=false;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public Status getRecentRecord(String ieme) {
		//获取最近的一条记录
		List<Status> total;
		Session session = null;
		try {
			session = dao.OpenSession();
			Criteria criteria = session.createCriteria(Status.class);
			criteria.add(Restrictions.eq("IEME",ieme));//找到IEME为ieme的记录
			criteria.addOrder(Order.desc("ADDTIME"));
			total=criteria.list();
			if(total!=null) {
				//如果找到该用户
				if(total.size()>0) {
					//返回第一条记录
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
	
	public List<Status> getStatusByIEME(String ieme) {
		return dao.getStatusByIEME(ieme);
	}
	
	public List<Status> getStatusByAreaName(String areaname) {
		return dao.getStatusByAreaName(areaname);
	}
}
