package org.kzcw.core;
import java.util.List;
import java.util.Map;
 
public interface BaseService<T> {
 
	public void save(List<T> t);
	public void save(T t);
	public void update(T t);//更新
	public void delete(T t);//删除
	public long findCount();//统计数量
	//查询
	public T findUniqueByProperty(String propertyName, Object value);
	public T findById(long id);
	public List<T> list();//查询所有
	public boolean ExecSQL(String sql);
	public List<T> findByExecSQL(String sql); 
	public List<Map> findMapByExecSQL(String sql);//根据sql语句查询结果,返回Map集合
}
