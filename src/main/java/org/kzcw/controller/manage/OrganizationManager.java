package org.kzcw.controller.manage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.kzcw.model.Organization;
import org.kzcw.service.AreaService;
import org.kzcw.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/manage/constructor")
public class OrganizationManager {
 //施工方管理
	
	@Autowired
	OrganizationService organizationservice;
	
	@Autowired
	AreaService areaservice;
	
	@RequestMapping(value = "/getview/constructorlist", method = RequestMethod.GET)
	public String onstructlist(ModelMap map,HttpServletRequest request){
		//获取施工方列表
		map.addAttribute("constructorllist",organizationservice.getOranizations());
		return "constructor/constructorlist";
	}
	
	@RequestMapping(value = "/getview/constructordeteil", method = RequestMethod.GET)
	public String constructordeteil(ModelMap map,HttpServletRequest request){
		//获取人员
		String id =request.getParameter("id");
		if(id!=null) {
			try {
				long cid = Long.valueOf(id);
				Organization company = organizationservice.findById(cid);
				if(company!=null) {
					map.addAttribute("company",company);
				}
				map.addAttribute("peoplelist",organizationservice.getPeopleByOrganizationID(cid));
			} catch (Exception e) {
			}
		}
		map.addAttribute("arealist", areaservice.list());
		return "constructor/constructordetil";
	}
	
	@RequestMapping(value = "/getview/operahistorylist", method = RequestMethod.GET)
	public String operahistorylist(ModelMap map,HttpServletRequest request){
		//获取施工方历史列表
		return "constructor/operahistorylist";
	}
	
	@RequestMapping(value = "/addconstructor", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addconstructor(ModelMap map, HttpServletRequest request) {
		// 添加箱体信息
		Map<String, String> result = new HashMap<String, String>();
		String name = request.getParameter("name");  
		String address = request.getParameter("address");  
		String telphone = request.getParameter("telphone"); 
		try {
			Organization organization=new Organization();
			organization.setNAME(name);
			organization.setADDRESS(address);
			organization.setTEL(telphone);
			organization.setDEPOSIT(500);
			organization.setTYPE(1); //设置类型为企业
			organization.setBELONGTO(0);
			organizationservice.save(organization);
			result.put("data", "true"); // 执行成功
		} catch (Exception e) {
			// TODO: handle exception
			result.put("data", "false");
		}
		return result;
	}
	
	@RequestMapping(value = "/deleteorgnization", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> deleteorgnization(ModelMap model, @RequestParam int ID, HttpServletRequest request) {
		// 删除
		Map<String, String> result = new HashMap<String, String>();
		Organization organization = organizationservice.findById(ID);
		if (organization != null) {
			try {
				List<Organization> peoplelist = organizationservice.getPeopleByOrganizationID(organization.getID());
				for(Organization o : peoplelist ) {
					//删除下属单位
					organizationservice.delete(o);
				}
				organizationservice.delete(organization);
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
	
	@RequestMapping(value = "/updateorgnization", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateorgnization(ModelMap model, @RequestParam int ID, HttpServletRequest request) {
		// 更新orgnization
		Map<String, String> result = new HashMap<String, String>();
		String editname = request.getParameter("editname");
		String editaddress = request.getParameter("editaddress");
		String edittelphone = request.getParameter("edittelphone");
		Organization organization = organizationservice.findById(ID);
		if (organization != null) {
			try {
				organization.setNAME(editname);
				organization.setADDRESS(editaddress);
				organization.setTEL(edittelphone);
				organizationservice.update(organization);
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
	
	@RequestMapping(value = "/getpeople", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getpeople(ModelMap map, HttpServletRequest request) {
		// 添加people
		Map<String, Object> result = new HashMap<String, Object>();
		String ID = request.getParameter("ID");  
		try {
			Organization organization=organizationservice.findById(Long.parseLong(ID));
			result.put("data", "true"); // 执行成功
			result.put("content", organization); // 执行成功
		} catch (Exception e) {
			result.put("data", "false");
		}
		return result;
	}
	
	
	@RequestMapping(value = "/addpeople", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addpeople(ModelMap map, HttpServletRequest request) {
		// 添加people
		Map<String, String> result = new HashMap<String, String>();
		String name = request.getParameter("name");  
		String address = request.getParameter("address");  
		String telphone = request.getParameter("telphone"); 
		String money = request.getParameter("money"); 
		String usertype = request.getParameter("usertype");
		String area = request.getParameter("area");
		String loginname = request.getParameter("loginname");
		String loginpasswd = request.getParameter("loginpasswd");
		String belongto = request.getParameter("belongto"); 
		try {
			Organization organization=new Organization();
			organization.setNAME(name);
			organization.setADDRESS(address);
			organization.setTEL(telphone);
			organization.setDEPOSIT(Integer.valueOf(money));
			organization.setUTYPE(Integer.valueOf(usertype));
			organization.setTYPE(0); //设置类型为个人
			organization.setAREANAME(area);
			organization.setSTATUS(1);
			organization.setLOGINNAME(loginname);
			organization.setPASSWD(loginpasswd);
			organization.setBELONGTO(Long.valueOf(belongto));
			organizationservice.save(organization);
			result.put("data", "true"); // 执行成功
		} catch (Exception e) {
			// TODO: handle exception
			result.put("data", "false");
		}
		return result;
	}
	
	@RequestMapping(value = "/editpeople", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> editpeople(ModelMap map, HttpServletRequest request) {
		// 编辑people
		Map<String, String> result = new HashMap<String, String>();
		String id = request.getParameter("id");
		String name = request.getParameter("editname");  
		String address = request.getParameter("editaddress");  
		String telphone = request.getParameter("edittelphone"); 
		String money = request.getParameter("editmoney"); 
		String usertype = request.getParameter("editusertype");
		String loginname = request.getParameter("editloginname");
		String loginpasswd = request.getParameter("editloginpasswd");
		String belongto = request.getParameter("editbelongto");
		String area = request.getParameter("editarea");
		try {
			Organization organization=organizationservice.findById(Long.parseLong(id));
			organization.setNAME(name);
			organization.setADDRESS(address);
			organization.setTEL(telphone);
			organization.setDEPOSIT(Integer.valueOf(money));
			organization.setUTYPE(Integer.valueOf(usertype));
			organization.setTYPE(0); //设置类型为个人
			organization.setAREANAME(area);
			organization.setSTATUS(1);
			organization.setLOGINNAME(loginname);
			organization.setPASSWD(loginpasswd);
			organization.setBELONGTO(Long.valueOf(belongto));
			organizationservice.update(organization);
			result.put("data", "true"); // 执行成功
		} catch (Exception e) {
			// TODO: handle exception
			result.put("data", "false");
		}
		return result;
	}
	
	@RequestMapping(value = "/deletepeople", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> deletepeople(ModelMap map, HttpServletRequest request) {
		// delete people
		Map<String, String> result = new HashMap<String, String>();
		String ID = request.getParameter("ID");  
		try {
			Organization organization=organizationservice.findById(Long.parseLong(ID));
			organizationservice.delete(organization);
			result.put("data", "true"); // 执行成功
		} catch (Exception e) {
			result.put("data", "false");
		}
		return result;
	}
	
	@RequestMapping(value = "/openuse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> openuse(ModelMap model, @RequestParam int ID, HttpServletRequest request) {
		// 启用
		Map<String, String> result = new HashMap<String, String>();
		Organization organization=organizationservice.findById(ID);
		if (organization != null) {
			try {
				organization.setSTATUS(1);
				organizationservice.update(organization);
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
		Organization organization=organizationservice.findById(ID);
		if (organization != null) {
			try {
				organization.setSTATUS(0);
				organizationservice.update(organization);
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
