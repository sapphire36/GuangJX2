package org.kzcw.controller.manage;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.kzcw.model.Role;
import org.kzcw.model.User;
import org.kzcw.service.AreaService;
import org.kzcw.service.RoleService;
import org.kzcw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manage/user")
public class UserManager {
	// 用户管理

	@Autowired
	UserService userservice;

	@Autowired
	AreaService areaservice;

	@Autowired
	RoleService roleservice;

	@RequestMapping(value = "/getview/userlist", method = RequestMethod.GET)
	public String indexview(ModelMap map, HttpServletRequest request) {
		// 获取用户列表
		map.addAttribute("userlist", userservice.list());
		map.addAttribute("arealist", areaservice.list());
		map.addAttribute("rolelist", roleservice.list());

		return "user/userlist";
	}

	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	@ResponseBody
	public User getUser(ModelMap model, @RequestParam int ID, HttpServletRequest request) {
		// 获取设备
		User user = userservice.findById(ID);
		return user;
	}

	@RequestMapping(value = "/doupdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> doupdate(ModelMap model, @RequestParam int ID, HttpServletRequest request) {
		// 更新设备
		Map<String, String> result = new HashMap<String, String>();
		String username = request.getParameter("username");
		String area = request.getParameter("area");
		String usertype = request.getParameter("usertype");
		User user = userservice.findById(ID);
		if (user != null) {
			try {
				Role role = roleservice.findUniqueByProperty("NAME", usertype);
				if (role != null) {
					user.setUSERNAME(username);
					user.setAREANAME(area);
					user.setROLEID(role.getID());
					userservice.update(user);
					result.put("data", "true");	
				}else {
					result.put("data", "false");
					result.put("message","not found role");
				}
			} catch (Exception e) {
				result.put("data", "false");
				result.put("message", e.toString());
			}
		} else {
			result.put("data", "false");
			result.put("message", "user not found!" + ID);
		}
		return result;
	}

	@RequestMapping(value = "/doadd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> doadd(ModelMap model, HttpServletRequest request) {
		// 新增
		Map<String, String> result = new HashMap<String, String>();
		String username = request.getParameter("username");
		String passwd = request.getParameter("passwd");
		String area = request.getParameter("area");
		String usertype = request.getParameter("usertype");
		Role role = roleservice.findUniqueByProperty("NAME", usertype);
		if (role != null) {
			User user = new User();
			user.setUSERNAME(username);
			user.setPASSWD(passwd);
			user.setAREANAME(area);
			user.setROLEID(role.getID());
			try {
				userservice.save(user);
				result.put("data", "true");
			} catch (Exception e) {
				result.put("data", "false");
				result.put("message", e.toString());
			}
		} else {
			result.put("data", "false");
			result.put("message", "role id not found!");
		}
		return result;
	}
	
	@RequestMapping(value = "/dodelete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> dodelete(ModelMap model, @RequestParam int ID, HttpServletRequest request) {
		// 删除
		Map<String, String> result = new HashMap<String, String>();
		User user = userservice.findById(ID);
		if (user != null) {
			try {
				userservice.delete(user);
				result.put("data", "true");
			} catch (Exception e) {
				result.put("data", "false");
				result.put("message", e.toString());
			}
		} else {
			result.put("data", "false");
			result.put("message", "user not found!" + ID);
		}
		return result;
	}
	
	@RequestMapping(value = "/openuse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> openuse(ModelMap model, @RequestParam int ID, HttpServletRequest request) {
		// 启用
		Map<String, String> result = new HashMap<String, String>();
		User user = userservice.findById(ID);
		if (user != null) {
			try {
				user.setSTATUS(1);
				userservice.update(user);
				result.put("data", "true");
			} catch (Exception e) {
				result.put("data", "false");
				result.put("message", e.toString());
			}
		} else {
			result.put("data", "false");
			result.put("message", "user not found!" + ID);
		}
		return result;
	}
	
	@RequestMapping(value = "/disableuse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> disableuse(ModelMap model, @RequestParam int ID, HttpServletRequest request) {
		// 禁用
		Map<String, String> result = new HashMap<String, String>();
		User user = userservice.findById(ID);
		if (user != null) {
			try {
				user.setSTATUS(0);
				userservice.update(user);
				result.put("data", "true");
			} catch (Exception e) {
				result.put("data", "false");
				result.put("message", e.toString());
			}
		} else {
			result.put("data", "false");
			result.put("message", "user not found!" + ID);
		}
		return result;
	}
}
