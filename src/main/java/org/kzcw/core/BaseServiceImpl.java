package org.kzcw.core;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.kzcw.common.global.SystemData;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseServiceImpl<T extends Serializable> implements BaseService<T> {

	@Autowired
	protected BaseDao<T> dao;

	public void setBaseDao(BaseDao<T> dao) {
		this.dao = dao;
	}

	public void save(T t) {
		General am = (General) t;
		am.setADDTIME(new Date());
		SystemData systemdata=SystemData.getInstance();
		//获取当前系统登录用户名
		dao.save(t);
	}
	
	public void save(List<T> t) {
		General am = (General) t;
		dao.save(t);
	}

	public void update(T t) {
		dao.update(t);
	}

	public void delete(T t) {
		dao.delete(t);
	}

	public long findCount() {
		return 1;
	}

	public T findUniqueByProperty(String propertyName, Object value) {
		return dao.findUniqByProperty(propertyName,value);
	}

	// 按照ID查找
	public T findById(long id) {
		return this.findUniqueByProperty("ID", id);
	}
	
	public List<T> list(){
		//返回列表
		List<T> total = null;
		total=dao.list();
		return total;
	}

	public boolean ExecSQL(String sql) {
		return dao.ExecSQL(sql);
	}

	public List<T> findByExecSQL(String querySql) {
		return dao.findEntryByExecSQL(querySql);
	}
	
	public List<Map> findMapByExecSQL(String sql){
		//根据sql语句查询结果,返回Map集合
		return dao.findMapByExecSQL(sql);
	}
}
