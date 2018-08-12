package org.kzcw.controller.manage;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.kzcw.common.AreaEntry;
import org.kzcw.common.InstallLightBoxList;
import org.kzcw.common.OperateList;
import org.kzcw.common.Iot.utils.OperateMessage;
import org.kzcw.common.global.Constant;
import org.kzcw.common.global.SystemData;
import org.kzcw.model.Breakhistory;
import org.kzcw.model.Lightbox;
import org.kzcw.model.Module;
import org.kzcw.model.User;
import org.kzcw.service.LightboxService;
import org.kzcw.service.ModuleService;
import org.kzcw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manage/home")
public class HomeManager {
	// 主页管理
	@Autowired
	LightboxService lservice;

	@Autowired
	ModuleService mservice;
	
	@Autowired
	private UserService userservice;

	@RequestMapping(value = "/getview/index", method = RequestMethod.GET)
	public String indexview(ModelMap map, HttpServletRequest request) {
		return "/home/index";
	}
	
	@RequestMapping(value = "/getmessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getmessage(ModelMap map, HttpServletRequest request) {
		// 获取消息
		Map<String, Object> result = new HashMap<String, Object>();
		Object userid = request.getSession().getAttribute(Constant.USERID);
		if (userid!= null) { 
			User user = userservice.findById(Long.parseLong((String) userid));
			SystemData systemdata = SystemData.getInstance();
			AreaEntry areaEntry =systemdata.getAreaEntry(user.getAREANAME());
			if(areaEntry!=null) {
				List<OperateMessage> operatelist=areaEntry.getOperateList().getOperateMessageList();
				List<Lightbox> installlist = areaEntry.getInstalllightboxlist().getChecklist();
				StringBuffer stringBuffer = new StringBuffer();
				int max = 7;
				for(OperateMessage operate : operatelist) {
					max--;
					if (max < 0) {
						// 最多显示10条记录
						break;
					}
					stringBuffer.append("<li class=\"unread\">");
					stringBuffer.append("<a href=\"/GuangJX/manage/control/getview/getcontrolview\">");
					stringBuffer.append("<span class=\"sender\">");
					stringBuffer.append(operate.EMEI);
					stringBuffer.append("</span>");
					stringBuffer.append("<span class=\"message\">");
					stringBuffer.append("test");
					stringBuffer.append("</span>");
					stringBuffer.append("<span class=\"time\">");
					if(operate.isOpen)
						stringBuffer.append("请求开锁");
					else
						stringBuffer.append("请求关锁");
					stringBuffer.append("</span>");
					stringBuffer.append("</a></li>");
				}
				
				for(Lightbox lightbox : installlist) {
					max--;
					if (max < 0) {
						// 最多显示10条记录
						break;
					}
					stringBuffer.append("<li class=\"unread\">");
					stringBuffer.append("<a href=\"/GuangJX/manage/device/getview/checklist\">");
					stringBuffer.append("<span class=\"sender\">");
					stringBuffer.append(lightbox.getNAME());
					stringBuffer.append("</span>");
					stringBuffer.append("<span class=\"message\">");
					stringBuffer.append("请求审核");
					stringBuffer.append("</span>");
					stringBuffer.append("<span class=\"time\">");
					stringBuffer.append(new Date().toString());
					stringBuffer.append("</span>");
					stringBuffer.append("</a></li>");
				}
				
				result.put("data", "true");
				result.put("size", operatelist.size()+installlist.size());
				result.put("message", stringBuffer.toString());
			}else {
				result.put("data", "false");
				result.put("size", 0);
				result.put("message", "true");
			}
		}
		return result;
	}

	@RequestMapping(value = "/getwainning", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getwainning(ModelMap map, HttpServletRequest request) {
		// 获取报警数据
		Map<String, Object> result = new HashMap<String, Object>();
		Object userid = request.getSession().getAttribute(Constant.USERID);
		if (userid!= null) { 
			User user = userservice.findById(Long.parseLong((String) userid));
			SystemData systemdata = SystemData.getInstance();
			AreaEntry areaEntry =systemdata.getAreaEntry(user.getAREANAME());
			if(areaEntry!=null) {
				List<Breakhistory> breakhistorylist=areaEntry.getBreakhistorylist().getBreakHistoryList();
				StringBuffer stringBuffer = new StringBuffer();
				int max = 7;
				for(Breakhistory breakhistory : breakhistorylist) {
					max--;
					if (max < 0) {
						// 最多显示10条记录
						break;
					}
					stringBuffer.append("<li class=\"unread\">");
					stringBuffer.append("<a href=\"/GuangJX/manage/device/getview/breakhistorylist\">");
					stringBuffer.append("<span class=\"message\">");
					stringBuffer.append(breakhistory.getIEME());
					stringBuffer.append("</span>");
					stringBuffer.append("<span class=\"time\">");
					stringBuffer.append(breakhistory.getTYPE());
					stringBuffer.append("</span>");
					stringBuffer.append("</a></li>");
				}
				result.put("data", "true");
				result.put("size", breakhistorylist.size());
				result.put("message", stringBuffer.toString());
			}else {
				result.put("data", "false");
				result.put("size", 0);
				result.put("message", "true");
			}
		}
		return result;
	}

	@RequestMapping(value = "/getview/addcheck", method = RequestMethod.GET)
	public String addcheck(ModelMap map, HttpServletRequest request) {
		// 单例模式实例化
		Object userid = request.getSession().getAttribute(Constant.USERID);
		if (userid!= null) { 
			User user = userservice.findById(Long.parseLong((String) userid));
			SystemData systemdata= SystemData.getInstance();
			if(user!=null) {
				AreaEntry areaEntry = systemdata.getAreaEntry(user.getAREANAME());
				if(areaEntry!=null) {
					InstallLightBoxList installlist=areaEntry.getInstalllightboxlist();
					Lightbox box = new Lightbox();
					box.setAREANAME("liuzirui");
					box.setNAME("EEDS_1");
					box.setIEME("5645657543502");
					box.setLOCATION("74,23.5");
					installlist.addItem(box);
				} 
			}
		}
		return "/home/index";
	}
	
	@RequestMapping(value = "/getview/openLock", method = RequestMethod.GET)
	public String openLock(ModelMap map,HttpServletRequest request) {
		// 开锁申请
		Object userid = request.getSession().getAttribute(Constant.USERID);
		if (userid!= null) { 
			User user = userservice.findById(Long.parseLong((String) userid));
			SystemData systemdata= SystemData.getInstance();
			if(user!=null) {
				AreaEntry areaEntry = systemdata.getAreaEntry(user.getAREANAME());
				if(areaEntry!=null) {
					OperateList operateList = areaEntry.getOperateList();;
					if (operateList != null) {
						OperateMessage message = new OperateMessage("test", "234234",true);
						// message;
						message.USERNAME = "test";
						message.time = new Date().toString();
						message.EMEI = "234234";
						operateList.addOperateItem(message);
					}
				} 
			}
		}
		/*OpenLockList list = OpenLockList.getInstance();
		OpenMessage message = new OpenMessage("test", "234234");
		// message;
		message.USERNAME = "test";
		message.time = new Date().toString();
		message.EMEI = "234234";
		list.AddItem(message);
		*/
		return "/home/index";
	}

	@RequestMapping(value = "/additem", method = RequestMethod.GET)
	public String additem(ModelMap map, HttpServletRequest request) {
		// NAME,int CODE,int ISLEAF,String URL,String IMAGE,int PARRENTCODE
		List<Module> list = new ArrayList<Module>();
		list.add(new Module("首页", 1, 0, "/GuangJX/manage/home/getview/index", "i-home", 0));

		list.add(new Module("业务操作", 2, 0, "", "i-list", 2));
		list.add(new Module("监控地图", 3, 1, "/GuangJX/manage/monitor/getview/getmap", "", 2));
		list.add(new Module("施工审核", 4, 1, "/GuangJX/manage/control/getview/getcontrolview", "", 2));

		list.add(new Module("设备管理", 5, 0, "", "i-list", 5));
		list.add(new Module("光交箱管理", 6, 1, "/GuangJX/manage/device/getview/lightboxlist", "", 5));
		list.add(new Module("安装审核", 7, 1, "/GuangJX/manage/device/getview/checklist", "", 5));
		list.add(new Module("告警列表", 8, 1, "/GuangJX/manage/device/getview/breakhistorylist", "", 5));
		list.add(new Module("安装审核列表", 9, 1, "/GuangJX/manage/device/getview/checklist", "", 5));

		list.add(new Module("施工方管理", 10, 0, "", "i-list", 10));
		list.add(new Module("施工人员管理", 11, 1, "/GuangJX/manage/constructor/getview/constructorlist", "", 10));
		list.add(new Module("施工历史", 12, 1, "/GuangJX/manage/constructor/getview/operahistorylist", "", 10));

		list.add(new Module("用户管理", 13, 0, "#", "i-list", 13));
		list.add(new Module("区域管理", 14, 1, "/GuangJX/manage/area/getview/arealist", "", 13));
		list.add(new Module("人员管理", 15, 1, "/GuangJX/manage/user/getview/userlist", "", 13));
		list.add(new Module("权限管理", 16, 1, "/GuangJX/manage/role/getview/rolelist", "", 13));

		list.add(new Module("系统设置", 17, 0, "", "i-list", 17));
		list.add(new Module("系统参数设置", 18, 1, "/GuangJX/manage/system/getview/setting", "", 17));
		list.add(new Module("报警触发器", 19, 1, "/GuangJX/manage/system/getview/warntriggerset", "", 17));
		list.add(new Module("系统日志", 20, 1, "/GuangJX/manage/system/getview/journallist", "", 17));
		// mservice.save(t);
		mservice.deleteAllItem();// 清空表
		for (Module m : list) {
			mservice.save(m);
		}
		return "/home/index";
	}
}