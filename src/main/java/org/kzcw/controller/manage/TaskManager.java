package org.kzcw.controller.manage;

import java.util.Date;
import java.util.List;

import org.kzcw.common.AreaEntry;
import org.kzcw.common.SysLogList;
import org.kzcw.common.WaitPublishQueue;
import org.kzcw.common.Iot.youren.OperaType;
import org.kzcw.common.Iot.youren.ResultData;
import org.kzcw.common.global.Constant;
import org.kzcw.common.global.SystemData;
import org.kzcw.model.Area;
import org.kzcw.model.Breakhistory;
import org.kzcw.model.Lightbox;
import org.kzcw.model.Status;
import org.kzcw.model.Systemlogs;
import org.kzcw.service.AreaService;
import org.kzcw.service.BreakhistoryService;
import org.kzcw.service.LightboxService;
import org.kzcw.service.StatusService;
import org.kzcw.service.SystemlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class TaskManager {
	// 定时器管理器

	// 故障历史表
	@Autowired
	BreakhistoryService breakservice;

	// 设备状态历史记录
	@Autowired
	StatusService staservice;

	@Autowired
	AreaService areaservice;

	@Autowired
	LightboxService lightboxservice;

	@Autowired
	SystemlogsService logservice;

	// 每个5秒执行一次
	@Scheduled(cron = "0/30 * * * * ? ")
	public void statusreport() {
		// 状态上报数据
		SystemData systemdata = SystemData.getInstance();
		List<AreaEntry> arealist = systemdata.getAreaEntryList();
		if (arealist.size() > 0) {
			for (AreaEntry areaEntry : arealist) {
				List<Status> statuslist = areaEntry.getStatuslist();
				if (statuslist.size() > 0) {
					for (Status s : statuslist) {
						try {
							Lightbox lightbox = lightboxservice.findByIMEI(s.getIEME());
							lightbox.setUNLOCKSTATUS(s.getUNLOCKSTATUS());
							lightbox.setDOORSTATUS(s.getDOORSTATUS());
							lightboxservice.update(lightbox);
							//**********
							int result = getWarnStatus(s); 
							if (result!=Constant.WARN_NORMAL) {
								Breakhistory breakhistory = new Breakhistory();
								breakhistory.setIEME(s.getIEME()); 
								breakhistory.setISHADND(0);//未被处理
								breakhistory.setTYPE(getWarnString(result));
								breakhistory.setADDTIME(new Date());
								breakhistory.setAREANAME(areaEntry.getArea().getAREANAME());
								breakservice.save(breakhistory);
							}
							//**********
							staservice.save(s);
						} catch (Exception e) {
							SysLogList logmanager = areaEntry.getSysloglist();
							logmanager.addLog("自动关锁异常:" + e.getMessage(), Constant.LOG_ERROR);// 添加日志
						}
					}
					areaEntry.getStatuslist().clear();
				}
			}
		}
	}
	

	/*
	 * int result = checkWarning(newdevice); if (result!=Constant.WARN_NORMAL) {
	 * Breakhistory breakhistory = new Breakhistory();
	 * breakhistory.setIEME(newdevice.DeviceID); breakhistory.setISHADND(0);//未被处理
	 * switch (result) { case Constant.WARN_LOW_TEMRATURE:
	 * breakhistory.setTYPE("温度过低"); break; case Constant.WARN_HIGH_TEMRATURE:
	 * breakhistory.setTYPE("温度过高"); break; case Constant.WARN_LOW_VOLUME:
	 * breakhistory.setTYPE("电压过低"); break; case Constant.WARN_HIGH_VOLUME:
	 * breakhistory.setTYPE("电压过高"); break; case Constant.WARN_ILLEGAL_OPENDOOR:
	 * breakhistory.setTYPE("非法或应急开锁"); break; WARN_UNKOWN case
	 * Constant.WARN_MECHAN_BREAKDOWN: breakhistory.setTYPE("机械故障"); break; }
	 * breakhistory.setADDTIME(new Date());
	 * breakhistory.setAREANAME(areaEntry.getArea().getAREANAME());
	 * areaEntry.getBreakhistorylist().addItem(breakhistory); }
	 */

	// 每个5秒执行一次
	@Scheduled(cron = "0/20 * * * * ? ")
	public void logschedule() {
		// 日志数据处理
		SystemData systemdata = SystemData.getInstance();
		List<AreaEntry> arealist = systemdata.getAreaEntryList();
		if (arealist.size() > 0) {
			for (AreaEntry areaEntry : arealist) {
				List<Systemlogs> systemlogslist = areaEntry.getSysloglist().getLogList();
				if (systemlogslist.size() > 0) {
					for (Systemlogs log : systemlogslist) {
						logservice.save(log);
					}
					areaEntry.getSysloglist().getLogList().clear();
				}
			}
		}
	}

	// 每个5秒执行一次
	@Scheduled(cron = "0/60 * * * * ? ")
	public void autoCloseLightbox() {
		// 自动关锁
		SystemData systemdata = SystemData.getInstance();
		List<AreaEntry> arealist = systemdata.getAreaEntryList();
		if (arealist.size() > 0) {
			for (AreaEntry areaEntry : arealist) {
				WaitPublishQueue queque = areaEntry.getWaitpublishqueue();
				SysLogList logmanager = areaEntry.getSysloglist();
				try {
					List<Lightbox> lightboxlist = lightboxservice.getList(areaEntry.getArea().getAREANAME());
					for (Lightbox box : lightboxlist) {
						//if (box.getCONSTRUCTSTATUS() == 1) {// 正在施工
							if (box.getUNLOCKSTATUS() == 0) {
								queque.addItem(new OperaType(box.getIEME(), 0)); // 加入关锁队列
								logmanager.addLog(box.getAREANAME() + "区域,设备名为:" + box.getNAME() + "的锁子已加入自动关锁队列",
										Constant.LOG_MESSAGE);// 添加日志
							}
						//}
					}
				} catch (Exception e) {
					logmanager.addLog("自动关锁异常:" + e.getMessage(), Constant.LOG_ERROR);// 添加日志
				}
			}
		}
	}
	
	// 每个分钟执行一次
	@Scheduled(cron = "0/30 * * * * ? ")
	public void demonschedule() {
		// 守护进程
		List<Area> list = areaservice.list();
		SystemData systemdata = SystemData.getInstance();
		for (Area area : list) {
			if (area.getISSUBSCRIBE() == 1) {
				if (systemdata.getAreaEntry(area.getAREANAME()) == null) {
					AreaEntry areaEntry = new AreaEntry(area);
					systemdata.addAreaEntry(areaEntry);
				}
			}
		}
	}

	private String getWarnString(int flag) {
		String result = "";
		switch (flag) {
		case Constant.WARN_LOW_TEMRATURE:
			result = "温度过低";
			break;
		case Constant.WARN_HIGH_TEMRATURE:
			result = "温度过高";
			break;
		case Constant.WARN_LOW_VOLUME:
			result = "电压过低";
			break;
		case Constant.WARN_HIGH_VOLUME:
			result = "电压过高";
			break;
		case Constant.WARN_ILLEGAL_OPENDOOR:
			result = "非法或应急开锁";
			break;
		case Constant.WARN_UNKOWN:
			result = "未知异常";
			break;
		case Constant.WARN_MECHAN_BREAKDOWN:
			result = "机械故障";
			break;
		}
		return result;
	}

	private int getWarnStatus(Status data) {
		// 检查故障情况
		try {
			float temperature = Float.valueOf(data.getTEMPERATURE());
			float volt = Float.valueOf(data.getVOLTAGE());
			if (volt < Constant.MIN_VOLUME) {
				return Constant.WARN_LOW_VOLUME;
			}
			if ((data.getLOCKSTATUS() == 0) && (data.getDOORSTATUS() == 1)) {
				return Constant.WARN_ILLEGAL_OPENDOOR;
			}
			if (data.getLOCKSTATUS() == data.getUNLOCKSTATUS()) {
				return Constant.WARN_MECHAN_BREAKDOWN;
			}
			if (temperature < Constant.MIN_TEMRATURE) {
				return Constant.WARN_LOW_TEMRATURE;
			}
			if (temperature > Constant.MAX_TEMRATURE) {
				return Constant.WARN_HIGH_TEMRATURE;
			}
			return Constant.WARN_NORMAL;
		} catch (Exception e) {
			return Constant.WARN_UNKOWN;
		}
	}
}
