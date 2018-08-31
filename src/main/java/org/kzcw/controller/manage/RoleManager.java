package org.kzcw.controller.manage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.kzcw.model.Module;
import org.kzcw.model.Role;
import org.kzcw.service.ModuleService;
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
	
	@Autowired
	ModuleService modulservice;
	
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
    
    @RequestMapping(value="/doeditrole",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> doeditrole(ModelMap model,@RequestParam long ID,HttpServletRequest request){
		//更新
    	Map<String,String> result=new HashMap<String,String>();
    	String NAME=request.getParameter("NAME");
    	if(NAME!=null) {
	    	Role role=roleservice.findById(ID);
	    	role.setNAME(NAME);
	    	roleservice.update(role);
	    	result.put("data", "true");
    	}else {
    		result.put("data", "false");
    	}
        return result;
    }
    
    @RequestMapping(value="/doeditright",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> doeditright(ModelMap model,@RequestParam long ID,HttpServletRequest request){
		//更新
    	Map<String,String> result=new HashMap<String,String>();
    	String rights=request.getParameter("RIHGT");
    	if(rights!=null) {
	    	Role role=roleservice.findById(ID);
	    	role.setRIGHTS(rights);
	    	roleservice.update(role);
	    	result.put("data", "true");
    	}else {
    		result.put("data", "false");
    	}
        return result;
    }
    
    private boolean isInRights(String[] arr,String value) {
    	for(int i=0;i<arr.length;i++) {
    		if(value.equals(arr[i])) {
    			return true;
    		}
    	}
    	return false;
    }
    
    @RequestMapping(value="/getrights",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> getrights(ModelMap model,HttpServletRequest request){
		//获取权限列表
    	Map<String,String> result=new HashMap<String,String>();
    	String id=request.getParameter("ID");
    	if(id!=null) {
    		long ID=Long.valueOf(id);
    	   	Role role=roleservice.findById(ID);
    	   	String rights = role.getRIGHTS();
    	   	String[] rightsArr = rights.split(",");
    		StringBuffer htmlstr = new StringBuffer();
    		List<Module> parentModule= modulservice.getParentList(); 
			//<li><input type="checkbox" onclick="pcheck()" id="" value="" name=""/> <label>Checkbox 1</label></li>
    		//<li><input type="checkbox" onclick="ccheck(this)" id="" value=""/> <label>Checkbox 1</label></li>
    		for(Module module:parentModule) {
    			 htmlstr.append("<div class=\"mws-form-row\"><label>");
    			 htmlstr.append(module.getNAME());
    			 htmlstr.append("</label><div class=\"mws-form-item clearfix\">");
    			 htmlstr.append("<ul class=\"mws-form-list inline\">");
    			 htmlstr.append("<li><input type=\"checkbox\" onclick=\"pcheck(");
    			 htmlstr.append(module.getCODE());
    			 htmlstr.append(")\" id=\"");
    			 htmlstr.append(module.getCODE());
    			 htmlstr.append("\" value=\"");
    			 htmlstr.append(module.getCODE());
    			 htmlstr.append("\" name=\"");
    			 htmlstr.append(module.getCODE());
    			 if(isInRights(rightsArr,String.valueOf(module.getCODE()))){
    				 htmlstr.append("\"checked/> <label>");
    			 }else {
    				 htmlstr.append("\"/> <label>");
    			 }
    			 htmlstr.append(module.getNAME());
    			 htmlstr.append("</label></li>");
    			 htmlstr.append("</ul>");
    			List<Module> chirldrens= modulservice.getChildren(module.getCODE());
    			if((chirldrens!=null)&&(chirldrens.size()>0)) {
    				htmlstr.append("<ul class=\"mws-form-list inline\">");
    				for (Module chirld:chirldrens) {
    	    			 htmlstr.append("<li><input type=\"checkbox\" onclick=\"ccheck(this)\" id=\"");
    	    			 htmlstr.append(chirld.getCODE());
    	    			 htmlstr.append("\" value=\"");
    	    			 htmlstr.append(chirld.getCODE());
    	    			 htmlstr.append("\" name=\"");
    	    			 htmlstr.append(module.getCODE());
    	    			 if(isInRights(rightsArr,String.valueOf(chirld.getCODE()))){
    	    				 htmlstr.append("\"checked/> <label>");
    	    			 }else {
    	    				 htmlstr.append("\"/> <label>");
    	    			 }
    	    			 htmlstr.append(chirld.getNAME());
    	    			 htmlstr.append("</label></li>");
    				}
    				htmlstr.append("</ul>");
    			}
        		htmlstr.append("</div></div>");
    		}
    		result.put("data", "true");
    		result.put("content", htmlstr.toString());
    	}else {
    		result.put("data", "false");
    	}
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
