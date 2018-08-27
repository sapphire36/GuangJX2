package org.kzcw.common;

import java.util.ArrayList;
import java.util.List;

import org.kzcw.model.Area;
import org.kzcw.model.Status;

public class AreaEntry {
	// 用户事务管理
	private List<UserSession> usersessionlist;
	private Area area;// 区域
	private ParsingReceiveQueue parsingqueue;// 解析队列
	private YouRenManger yourenmanager;// 有人网络
	private WaitPublishQueue waitpublishqueue;// 等待操作队列
	private OperateList operateList; // 开锁队列
	private SysLogList sysloglist;// 日志信息表
	private InstallLightBoxList installlightboxlist;// 安装审核信息表
	private List<Status> statuslist;//数据上报状态表
	public boolean IsWorked;

	public AreaEntry(Area area) {
		this.area = area;
		init();
        new Thread("Start work"){
            @Override
            public void run(){
            	yourenmanager.doStart();
            }
        }.start();
	}
	
	public UserSession getUsersession(String name) {
		for(UserSession usersession:usersessionlist) {
			if(usersession.getUser().getUSERNAME().equals(name)) {
				return usersession;
			}
		}
		return null;
	}

	private void init() {
		usersessionlist = new ArrayList<UserSession>();
		parsingqueue = new ParsingReceiveQueue(this);
		yourenmanager = new YouRenManger(this, area.getLOGINNAME(), area.getPASSWD());
		waitpublishqueue = new WaitPublishQueue(this);
		operateList = new OperateList();
		sysloglist = new SysLogList(this);
		installlightboxlist = new InstallLightBoxList();
		statuslist= new ArrayList<Status>();
	}

	public List<UserSession> getUsersessionlist() {
		return usersessionlist;
	}

	public void setUsersessionlist(List<UserSession> usersessionlist) {
		this.usersessionlist = usersessionlist;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public ParsingReceiveQueue getParsingqueue() {
		return parsingqueue;
	}

	public void setParsingqueue(ParsingReceiveQueue parsingqueue) {
		this.parsingqueue = parsingqueue;
	}

	public YouRenManger getYourenmanager() {
		return yourenmanager;
	}

	public void setYourenmanager(YouRenManger yourenmanager) {
		this.yourenmanager = yourenmanager;
	}

	public WaitPublishQueue getWaitpublishqueue() {
		return waitpublishqueue;
	}

	public void setWaitpublishqueue(WaitPublishQueue waitpublishqueue) {
		this.waitpublishqueue = waitpublishqueue;
	}

	public OperateList getOperateList() {
		return operateList;
	}

	public void setOperateList(OperateList operateList) {
		this.operateList = operateList;
	}

	public SysLogList getSysloglist() {
		return sysloglist;
	}

	public void setSysloglist(SysLogList sysloglist) {
		this.sysloglist = sysloglist;
	}

	public InstallLightBoxList getInstalllightboxlist() {
		return installlightboxlist;
	}

	public void setInstalllightboxlist(InstallLightBoxList installlightboxlist) {
		this.installlightboxlist = installlightboxlist;
	}

	public List<Status> getStatuslist() {
		return statuslist;
	}

	public void setStatuslist(List<Status> statuslist) {
		this.statuslist = statuslist;
	}
}
