package org.kzcw.controller.manage;
import java.util.List;

import org.kzcw.common.AreaEntry;
import org.kzcw.common.global.SystemData;
import org.kzcw.model.Area;
import org.kzcw.model.Breakhistory;
import org.kzcw.model.Status;
import org.kzcw.model.Systemlogs;
import org.kzcw.service.AreaService;
import org.kzcw.service.BreakhistoryService;
import org.kzcw.service.StatusService;
import org.kzcw.service.SystemlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class TaskManager {
	//定时器管理器
	
	// 故障历史表
	@Autowired
	BreakhistoryService breakservice;
	
	// 设备状态历史记录
	@Autowired
	StatusService staservice;
	
	@Autowired
	AreaService areaservice;
	
	@Autowired
	SystemlogsService logservice;
	
	// 每个5秒执行一次
	@Scheduled(cron = "0/20 * * * * ? ")
	public void statusreport() {
		//状态上报数据
		SystemData systemdata = SystemData.getInstance();
		List<AreaEntry> arealist=systemdata.getAreaEntryList();
		if (arealist.size() > 0) {
			for (AreaEntry areaEntry : arealist) {
				List<Status> statuslist=areaEntry.getStatuslist();
				if(statuslist.size()>0) {
					for(Status s:statuslist) {
						staservice.save(s);
					}
					areaEntry.getStatuslist().clear();
				}
			}
		}
	}
	
	// 每个5秒执行一次
	@Scheduled(cron = "0/20 * * * * ? ")
	public void logschedule() {
		//日志数据处理
		SystemData systemdata = SystemData.getInstance();
		List<AreaEntry> arealist=systemdata.getAreaEntryList();
		if (arealist.size() > 0) {
			for (AreaEntry areaEntry : arealist) {
				List<Systemlogs> systemlogslist=areaEntry.getSysloglist().getLogList();
				if(systemlogslist.size()>0) {
					for(Systemlogs log:systemlogslist) {
						logservice.save(log);
					}
					areaEntry.getSysloglist().getLogList().clear();
				}
			}
		}
	}
	
	// 每个5秒执行一次
	@Scheduled(cron = "0/20 * * * * ? ")
	public void breakhistoryschedule() {
		//日志数据处理
		SystemData systemdata = SystemData.getInstance();
		List<AreaEntry> arealist=systemdata.getAreaEntryList();
		if (arealist.size() > 0) {
			for (AreaEntry areaEntry : arealist) {
				List<Breakhistory> breakhistoryList=areaEntry.getBreakhistorylist().getBreakHistoryList();
				if(breakhistoryList.size()>0) {
					for(Breakhistory breakhistory:breakhistoryList) {
						breakservice.save(breakhistory);
					}
					areaEntry.getBreakhistorylist().getBreakHistoryList().clear();
				}
			}
		}
	}
	
	// 每个分钟执行一次
	@Scheduled(cron = "0/30 * * * * ? ")
	public void demonschedule() {
		//守护进程
		List<Area> list = areaservice.list();
		SystemData systemdata = SystemData.getInstance();
		for(Area area : list) {
			if(area.getISSUBSCRIBE()==1) {
				if(systemdata.getAreaEntry(area.getAREANAME())==null) {
					AreaEntry areaEntry = new AreaEntry(area);
					systemdata.addAreaEntry(areaEntry);
				}
			}
		}
	}
}
