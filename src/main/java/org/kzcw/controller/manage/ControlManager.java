package org.kzcw.controller.manage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.kzcw.common.AreaEntry;
import org.kzcw.common.OperateList;
import org.kzcw.common.WaitPublishQueue;
import org.kzcw.common.Iot.utils.OperateMessage;
import org.kzcw.common.Iot.youren.OperaType;
import org.kzcw.common.global.Constant;
import org.kzcw.common.global.SystemData;
import org.kzcw.model.User;
import org.kzcw.service.BreakhistoryService;
import org.kzcw.service.LightboxService;
import org.kzcw.service.OperatehistoryService;
import org.kzcw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manage/control")
public class ControlManager {
	// 开锁,关锁队列管理

	@Autowired
	LightboxService lservice;

	@Autowired
	OperatehistoryService operahistory;

	@Autowired
	private UserService userservice;
	// 故障历史表
	@Autowired
	BreakhistoryService breakservice;

	@RequestMapping(value = "/getview/getcontrolview", method = RequestMethod.GET)
	public String getcontrolview(ModelMap model, HttpServletRequest request) {
		// 控制界面
		return "/control/control";
	}

	@RequestMapping(value = "/gettodolist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> gettodolist(ModelMap model, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		Object userid = request.getSession().getAttribute(Constant.USERID);
		StringBuffer stringBuffer = new StringBuffer();
		if (userid != null) {
			User user = userservice.findById(Long.parseLong((String) userid));
			SystemData systemdata = SystemData.getInstance();
			AreaEntry areaEntry = systemdata.getAreaEntry(user.getAREANAME());
			if (areaEntry != null) {
				List<OperaType>  waitList = areaEntry.getWaitpublishqueue().getList();
				for (OperaType x : waitList) {
					stringBuffer.append("<li>");
					stringBuffer.append(x.EMEI);
					stringBuffer.append("</span>");
					if(x.type==1) {
						stringBuffer.append("正在等待执行开锁操作");
					}else {
						stringBuffer.append("正在等待执行关锁操作");
					}
					stringBuffer.append("</li>");
				}
			} else {
				result.put("data", "false");
				result.put("message", "该区域没有设备没有订阅,请联系管理员!");
			}
		}
		result.put("data", "true");
		result.put("content", stringBuffer.toString());
		return result;
	}

	@RequestMapping(value = "/getoperatelist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getoperatelist(ModelMap model, HttpServletRequest request) {
		// 获取开锁列表
		Map<String, Object> result = new HashMap<String, Object>();
		Object userid = request.getSession().getAttribute(Constant.USERID);
		StringBuffer stringBuffer = new StringBuffer();
		if (userid != null) {
			User user = userservice.findById(Long.parseLong((String) userid));
			SystemData systemdata = SystemData.getInstance();
			AreaEntry areaEntry = systemdata.getAreaEntry(user.getAREANAME());
			if (areaEntry != null) {
				OperateList operateList = areaEntry.getOperateList();
				for (OperateMessage x : operateList.list) {
					stringBuffer.append("<li onclick=\"showdialog(this)\"><span>");
					stringBuffer.append(x.USERNAME);
					stringBuffer.append("</span>");
					if(x.isOpen) {
						stringBuffer.append(":正在请求开锁:" + x.EMEI + ":操作");
					}else {
						stringBuffer.append(":正在请求关锁:" + x.EMEI + ":操作");
					}
					stringBuffer.append("</li>");
				}
			} else {
				result.put("data", "false");
				result.put("message", "该区域没有设备没有订阅,请联系管理员!");
			}
		}
		result.put("data", "true");
		result.put("content", stringBuffer.toString());
		return result;
	}

	@RequestMapping(value = "/dooperatelock", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doopenlock(ModelMap model, @RequestParam String IMEI, HttpServletRequest request) {
		// 执行开锁操作
		Map<String, Object> result = new HashMap<String, Object>();
		Object userid = request.getSession().getAttribute(Constant.USERID);
		if (userid != null) {
			User user = userservice.findById(Long.parseLong((String) userid));
			SystemData systemdata = SystemData.getInstance();
			AreaEntry areaEntry = systemdata.getAreaEntry(user.getAREANAME());
			if (areaEntry != null) {
				WaitPublishQueue queque = areaEntry.getWaitpublishqueue();
				OperateMessage message = areaEntry.getOperateList().getItemByEMEI(IMEI);
				if(message.isOpen)
				    queque.addItem(new OperaType(message.EMEI, 1));// 加入开锁队列
				else
					queque.addItem(new OperaType(message.EMEI, 0));// 加入关锁队列
				areaEntry.getOperateList().delItemByEMEI(IMEI);
				result.put("data", "true");
			} else {
				result.put("data", "false");
				result.put("message", "该区域没有设备没有订阅,请联系管理员!");
			}
		}
		result.put("data", "true");
		return result;
	}

	@RequestMapping(value = "/doorejectperate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doorejectperate(ModelMap model, @RequestParam String IMEI, HttpServletRequest request) {
		// 执行关锁操作
		Map<String, Object> result = new HashMap<String, Object>();
		Object userid = request.getSession().getAttribute(Constant.USERID);
		if (userid != null) {
			User user = userservice.findById(Long.parseLong((String) userid));
			SystemData systemdata = SystemData.getInstance();
			AreaEntry areaEntry = systemdata.getAreaEntry(user.getAREANAME());
			if (areaEntry != null) {
				areaEntry.getOperateList().delItemByEMEI(IMEI);
				result.put("data", "true");
			} else {
				result.put("data", "false");
				result.put("message", "该区域没有设备没有订阅,请联系管理员!");
			}
		}
		result.put("data", "true");
		return result;
	}

	@RequestMapping(value = "/dograde", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> dograde(ModelMap model, @RequestParam int ID, HttpServletRequest request) {
		// 执行评分操作
		Map<String, Object> result = new HashMap<String, Object>();
		/*
		 * GradeList list=GradeList.getInstance(); GradeMessge message=list.DelItem(ID);
		 * if(message!=null) { result.put("data","false"); return result; } try {
		 * Operatehistory history=new Operatehistory(); history.setBOXID(1);
		 * history.setORGANIZATIONID(105); history.setSCORE(7);
		 * operahistory.save(history); result.put("data","true"); } catch (Exception e)
		 * { // TODO: handle exception result.put("data","false"); }
		 */
		return result;
	}
}
