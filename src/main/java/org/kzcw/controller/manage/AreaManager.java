package org.kzcw.controller.manage;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.kzcw.common.AreaEntry;
import org.kzcw.common.global.SystemData;
import org.kzcw.model.Area;
import org.kzcw.service.AreaService;
import org.kzcw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manage/area")
public class AreaManager {
	// 用户管理

	@Autowired
	UserService userservice;

	@Autowired
	AreaService arearservice;

	@RequestMapping(value = "/getview/arealist", method = RequestMethod.GET)
	public String indexview(ModelMap map, HttpServletRequest request) {
		// 获取用户列表
		map.addAttribute("arealist", arearservice.list());
		return "area/arealist";
	}

	@RequestMapping(value = "/subscribe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> subscribe(ModelMap model, @RequestParam int ID, HttpServletRequest request) {
		// 订阅该区域
		Map<String, String> result = new HashMap<String, String>();
		Area area = arearservice.findById(ID);
		if (area != null) {
			area.setISSUBSCRIBE(1);
			SystemData systemdata = SystemData.getInstance();
			AreaEntry areaEntry = new AreaEntry(area);
			systemdata.addAreaEntry(areaEntry);
			arearservice.update(area);
			result.put("data", "true");
		} else {
			result.put("data", "没有找到该项记录!!");
			result.put("message", "false");
		}
		return result;
	}

	@RequestMapping(value = "/cancelsubscribe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> cancelsubscribe(ModelMap model, @RequestParam int ID, HttpServletRequest request) {
		// 订阅该区域
		Map<String, String> result = new HashMap<String, String>();
		Area area = arearservice.findById(ID);
		if (area != null) {
			area.setISSUBSCRIBE(0);
			SystemData systemdata = SystemData.getInstance();
			systemdata.delAreaEntry(area.getAREANAME());
			arearservice.update(area);
			result.put("data", "true");
		} else {
			result.put("data", "没有找到该项记录!!");
			result.put("message", "false");
		}
		return result;
	}

	@RequestMapping(value = "/addarea", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addarea(ModelMap map, HttpServletRequest request) {
		// 添加箱体信息
		Map<String, String> result = new HashMap<String, String>();
		try {
			String areaname = request.getParameter("areaname");
			String loginname = request.getParameter("loginname");
			String passwd = request.getParameter("passwd");
			Area area = new Area();
			area.setAREANAME(areaname);
			area.setLOGINNAME(loginname);
			area.setPASSWD(passwd);
			arearservice.save(area);
			result.put("data", "true"); // 执行成功
		} catch (Exception e) {
			// TODO: handle exception
			result.put("data", "false");
			result.put("message", e.toString());
		}
		return result;
	}

	@RequestMapping(value = "/deletearea", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> dodelete(ModelMap model, @RequestParam int ID, HttpServletRequest request) {
		// 删除设备
		Map<String, String> result = new HashMap<String, String>();
		Area area = arearservice.findById(ID);
		if (area != null) {
			arearservice.delete(area);
			result.put("data", "true");
		} else {
			result.put("data", "没有找到该项记录!!");
			result.put("message", "false");
		}
		return result;
	}
}
