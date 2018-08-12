package org.kzcw.controller.manage;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.kzcw.model.Role;
import org.kzcw.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manage/role")
public class RoleManager {
	//用户管理
	
	@Autowired
	RoleService roleservice;
	
	@RequestMapping(value = "/getview/rolelist", method = RequestMethod.GET)
	public String indexview(ModelMap map,HttpServletRequest request){
		//获取用户列表
		map.addAttribute("rolelist",roleservice.list());
		return "role/rolelist";
	}
	
    @RequestMapping(value="/getRole",method = RequestMethod.GET)
    @ResponseBody
    public Role getUser(ModelMap model,@RequestParam int ID,HttpServletRequest request){
		//获取设备
    	Role role=roleservice.findById(ID);
        return role;
    }
    
    @RequestMapping(value="/doupdate",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> doupdate(ModelMap model,@RequestParam int ID,HttpServletRequest request){
		//更新设备
    	Map<String,String> result=new HashMap<String,String>();
    	Role role=roleservice.findById(ID);
        return result;
    }
    
    @RequestMapping(value="/doadd",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> doadd(ModelMap model,HttpServletRequest request){
		//新增设备
    	Map<String,String> result=new HashMap<String,String>();
        return result;
    }
    
    @RequestMapping(value="/dodelete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> dodelete(ModelMap model,@RequestParam int ID,HttpServletRequest request){
		//删除设备
    	Map<String,String> result=new HashMap<String,String>();
    	Role role=roleservice.findById(ID);
        return result;
    }
}
