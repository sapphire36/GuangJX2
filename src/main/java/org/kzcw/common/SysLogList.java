package org.kzcw.common;

import java.util.ArrayList;
import java.util.List;

import org.kzcw.model.Systemlogs;

public class SysLogList {
	//日志类
	private List<Systemlogs> loglist;
	private AreaEntry areaEntry;
	public SysLogList(AreaEntry areaentry) {
		this.areaEntry=areaentry;
		loglist=new ArrayList<Systemlogs>();
	}
	
	public void addLog(String content,int type) {
		//type 1:代表消息  2:代表警告  3:代表故障
		Systemlogs systemlog=new Systemlogs(content,String.valueOf(type));
		systemlog.setAREANAME(areaEntry.getArea().getAREANAME());
		loglist.add(systemlog);
	}
	
	public boolean IsNotEmpty() {
		//判断是否为空
		if(loglist.size()>0) {
			return true;
		}else {
			return false;
		}
	}
	
	public  List<Systemlogs> getLogList() {
		//返回日志列表
		return loglist;
	}
}
