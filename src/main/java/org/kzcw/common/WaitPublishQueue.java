package org.kzcw.common;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kzcw.common.Iot.youren.OperaType;
public class WaitPublishQueue {
	//等待推送队列
	//hashmap 提高查询速度
	private Map<String,OperaType> list = new HashMap<String,OperaType>();
	public boolean IsFlush;  //是否刷新
	private AreaEntry usersession;

	public WaitPublishQueue(AreaEntry usersession) {
		this.usersession=usersession;
	}
	
	public List<OperaType> getList(){
		//返回MapSet
		List<OperaType> resultlist=new ArrayList<OperaType>();
        for(Map.Entry<String, OperaType> entry: list.entrySet())
        {
    		 resultlist.add(entry.getValue());
        }
		return resultlist;
	}
	
	public void addItem(OperaType type) {
		//添加项目
		IsFlush=true;
		list.put(type.EMEI,type);
	}
	
	public void delItem(String emei) {
		//删除项目
		IsFlush=true;
		list.remove(emei);
	}
	
	public OperaType getItem(String emei) {
		return list.get(emei);
	}
	
	public void doOperate(String EMEI) {
		//执行操作
		if(list.get(EMEI)!=null) {
			//如果在操作列表中存在改项
			IsFlush=true;
			OperaType opera=list.get(EMEI);
			if(opera.type==1) {
				//执行开锁
				usersession.getYourenmanager().doOpenLock(EMEI);
				usersession.getYourenmanager().doOpenLock(EMEI);
				delItem(EMEI);
			}else {
				//执行关锁
				usersession.getYourenmanager().doCloseLock(EMEI);
				usersession.getYourenmanager().doCloseLock(EMEI);
				delItem(EMEI);
			}
		}
	}
}
