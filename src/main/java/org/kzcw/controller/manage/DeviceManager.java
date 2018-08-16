package org.kzcw.controller.manage;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kzcw.common.AreaEntry;
import org.kzcw.common.InstallLightBoxList;
import org.kzcw.common.global.Constant;
import org.kzcw.common.global.Picdeliver;
import org.kzcw.common.global.SystemData;
import org.kzcw.model.Area;
import org.kzcw.model.Breakhistory;
import org.kzcw.model.Lightbox;
import org.kzcw.model.Status;
import org.kzcw.model.User;
import org.kzcw.service.BreakhistoryService;
import org.kzcw.service.LightboxService;
import org.kzcw.service.StatusService;
import org.kzcw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/manage/device")
public class DeviceManager {
	// 箱体信息管理
	@Autowired
	LightboxService lservice;
	// 设备状态历史记录
	@Autowired
	StatusService staservice;
	
	@Autowired
	private UserService userservice;
	// 故障历史表
	@Autowired
	BreakhistoryService breakservice;
	
	@RequestMapping("/showImage")
	public void showImage(HttpServletResponse response) {
		try {
			Picdeliver deliverpic=Picdeliver.getInstance();
			byte data[]=deliverpic.pic;
			response.setContentType("image/jpeg");
			OutputStream toClient=response.getOutputStream();
			toClient.write(data);
			toClient.flush();
			toClient.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("图片不存在");
		}
	}
/*
	@RequestMapping("/showImage")
	public void showImage(HttpServletRequest request,HttpServletResponse response) {
		try {
			String imageUrl=request.getParameter("filepath");
	        FileInputStream inputStream = new FileInputStream(imageUrl);  
	        int i = inputStream.available();  
	        byte[] buff = new byte[i];  
	        inputStream.read(buff);  
	        inputStream.close();  
	        response.setContentType("image/*");  
	        OutputStream out = response.getOutputStream();  
	        out.write(buff);  
	        out.close();  
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("图片不存在");
		}
	}
	*/
	// ************************************箱体信息管理**************************
	// ************************************箱体信息管理**************************
	@RequestMapping(value = "/getview/lightboxlist", method = RequestMethod.GET)
	public String lightboxlist(ModelMap model, HttpServletRequest request) {
		// 获取箱体信息列表
		Object userid = request.getSession().getAttribute(Constant.USERID);
		if (userid!= null) { 
			User user = userservice.findById(Long.parseLong((String) userid));
	        model.addAttribute("lightlist", lservice.getLightboxList(user.getAREANAME()));
		}
		return "/device/lightboxlist";
	}

	@RequestMapping(value = "/addlightbox", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addlightbox(ModelMap map, HttpServletRequest request) {
		// 添加箱体信息
		Map<String, String> result = new HashMap<String, String>();
		String name = request.getParameter("NAME"); // 获取参数NAME
		String lid = request.getParameter("LOCKID"); // 获取参数LOCKID
		String area = request.getParameter("AREA"); // 获取参数SPEC
		try {
			Lightbox ld = new Lightbox();
			ld.setNAME(name);
			ld.setIEME(lid);
			ld.setAREANAME(area);
			ld.setLOCATION("72,35.5");
			Object userid = request.getSession().getAttribute(Constant.USERID);
			if (userid != null) {
				//set who add lightbox
				User user = userservice.findById(Long.parseLong((String) userid));
				if(user!=null) {
					ld.setOPERATION(user.getUSERNAME());
				}
			}
			lservice.save(ld);
			result.put("data", "true"); // 执行成功
		} catch (Exception e) {
			// TODO: handle exception
			result.put("data", "false");
		}
		return result;
	}

	@RequestMapping(value = "/getlightbox", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> getlightbox(ModelMap map, HttpServletRequest request) {
		// 获取箱体信息
		Map<String, String> result = new HashMap<String, String>();
		String ID = request.getParameter("ID"); // 获取参数ID
		try {
			Lightbox ld = lservice.findUniqueByProperty("ID", Long.parseLong(ID));
			if (ld != null) {
				result.put("NAME", ld.getNAME());
				result.put("LOCKID", ld.getIEME());
				result.put("LOCATION", ld.getLOCATION());
				result.put("PEOPLE", ld.getNAME());
				result.put("data", "true");
			} else {
				result.put("data", "false");
			}

		} catch (Exception e) {
			// TODO: handle exception
			result.put("data", "false");
		}
		return result;
	}

	@RequestMapping(value = "/editlightbox", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> editlightbox(ModelMap map, HttpServletRequest request) {
		// 编辑箱体信息
		Map<String, String> result = new HashMap<String, String>();
		String name = request.getParameter("NAME"); // 获取参数NAME
		String lid = request.getParameter("LOCKID"); // 获取参数EMEI
		String loca = request.getParameter("LOCATION"); // 获取参数NAME
		String peo = request.getParameter("PEOPLE"); // 获取参数EMEI
		String ID = request.getParameter("ID"); // 获取参数ID
		try {
			Lightbox ld = lservice.findUniqueByProperty("ID", Long.parseLong(ID));
			ld.setNAME(name);
			ld.setIEME(lid);
			ld.setLOCATION(loca);
			ld.setPEOPLE(peo);
			lservice.update(ld);
			result.put("data", "true"); // 执行成功
		} catch (Exception e) {
			// TODO: handle exception
			result.put("data", "false");
		}
		return result;
	}

	@RequestMapping(value = "/deletelightbox", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> deletelightbox(ModelMap map, HttpServletRequest request) {
		// 删除箱体信息
		Map<String, String> result = new HashMap<String, String>();
		String ID = request.getParameter("ID"); // 获取参数ID
		try {
			Lightbox ld = lservice.findUniqueByProperty("ID", Long.parseLong(ID));
			lservice.delete(ld);
			result.put("data", "true"); // 执行成功
		} catch (Exception e) {
			// TODO: handle exception
			result.put("data", "false");
		}
		return result;
	}
	
	@RequestMapping(value = "/getview/detil", method = RequestMethod.GET)
	public String lightboxdetil(ModelMap model, HttpServletRequest request) {
		// 获取箱体信息列表
		String id=request.getParameter("id");
		if(id!=null) {
			Lightbox lightbox = lservice.findById(Long.parseLong(id));
		    model.addAttribute("lightbox", lightbox);
		}
		return "/device/detillightbox";
	}

	// ************************************光交箱状态管理**************************
	// ************************************光交箱状态管理**************************
	@RequestMapping(value = "/getview/getreport", method = RequestMethod.GET)
	public String getrephislist(ModelMap model, HttpServletRequest request) {
		// 获取设备数据上报
		String IEME = request.getParameter("IEME");
		if(IEME!=null) {
			List<Status> list = staservice.getStatusByIEME(IEME);
		    model.addAttribute("statuslist", list);
		}
		return "/device/reportlist";
	}
	
	@RequestMapping(value = "/getview/statuslist", method = RequestMethod.GET)
	public String statuslist(ModelMap model, HttpServletRequest request) {
		// 获取设备状态历史记录列表
		model.addAttribute("statuslist", staservice.list());
		return "/device/statuslist";
	}

	// ************************************故障历史表**************************
	// ************************************故障历史表**************************
	@RequestMapping(value = "/getview/breakhistorylist", method = RequestMethod.GET)
	public String breakhistorylist(ModelMap model, HttpServletRequest request) {
		// 获取设备状态历史记录列表
		Object userid = request.getSession().getAttribute(Constant.USERID);
		if (userid!= null) { 
			User user = userservice.findById(Long.parseLong((String) userid));
	        model.addAttribute("breaklist", breakservice.getBreakhistoryByAreaName(user.getAREANAME()));
		}
		return "/device/breakhistorylist";
	}
	
	
	@RequestMapping(value = "/handleBreak", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> handleBreak(ModelMap model, @RequestParam int ID, HttpServletRequest request) {
		// 处理故障
		Map<String, String> result = new HashMap<String, String>();
		Breakhistory breakhistory = breakservice.findById(ID);
		if (breakhistory != null) {
			breakhistory.setISHADND(1);
			breakservice.update(breakhistory);
			result.put("data", "true");
		} else {
			result.put("data", "没有找到该项记录!!");
			result.put("message", "false");
		}
		return result;
	}

	// ************************************安装审核队列管理**************************
	// ************************************安装审核队列管理**************************
	@RequestMapping(value = "/getview/checklist", method = RequestMethod.GET)
	public String getcontrolview(ModelMap model, HttpServletRequest request) {
		// 控制界面
		return "/device/checklist";
	}

	@RequestMapping(value = "/getchecklist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getchecklist(ModelMap map, HttpServletRequest request) {
		// 获取审核队列
		Map<String, Object> result = new HashMap<String, Object>();
		Object userid = request.getSession().getAttribute(Constant.USERID);
		StringBuffer stringBuffer = new StringBuffer();
		if (userid!= null) { 
			User user = userservice.findById(Long.parseLong((String) userid));
			SystemData systemdata= SystemData.getInstance();
			AreaEntry areaEntry = systemdata.getAreaEntry(user.getAREANAME());
			InstallLightBoxList installlist=areaEntry.getInstalllightboxlist();
			for (Lightbox x : installlist.getChecklist()) {
	        	stringBuffer.append("<li onclick=\"showdialog(this)\"><span>");
	    		stringBuffer.append(String.format("%s:%s:%s", x.getNAME(),x.getIEME(),x.getLOCATION()));
				stringBuffer.append("</span>");	
				stringBuffer.append("</li>");
			}
		}
		result.put("data", stringBuffer.toString());
		return result;
	}

	@RequestMapping(value = "/doPassCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doPassCheck(ModelMap map, HttpServletRequest request) {
		// 通过审核
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", "false");
		Object userid = request.getSession().getAttribute(Constant.USERID);
		if (userid!= null) {
			String editlightboxname=request.getParameter("editlightboxname");
			String editlockid=request.getParameter("editlockid");
			String editlocation=request.getParameter("editlocation");
			String editpeople=request.getParameter("editpeople");
			User user = userservice.findById(Long.parseLong((String) userid));
			SystemData systemdata= SystemData.getInstance();
			AreaEntry areaEntry = systemdata.getAreaEntry(user.getAREANAME());
			InstallLightBoxList installlist=areaEntry.getInstalllightboxlist();
			Lightbox box=installlist.delItemByEMEI(editlockid);
			if(box!=null) {			
				box.setIEME(editlockid);
				box.setNAME(editlightboxname); 
				box.setLOCATION(editlocation);
				box.setPEOPLE(editpeople); 
				box.setAREANAME(user.getAREANAME());
				lservice.save(box);
				result.put("data", "true");
			} 
		}
		return result;
	}

	@RequestMapping(value = "/doRejectCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doRejectCheck(ModelMap map, HttpServletRequest request) {
		// 拒绝审核
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", "false");
		Object userid = request.getSession().getAttribute(Constant.USERID);
		if (userid!= null) {
			String editlockid=request.getParameter("editlockid");
			User user = userservice.findById(Long.parseLong((String) userid));
			SystemData systemdata= SystemData.getInstance();
			AreaEntry areaEntry = systemdata.getAreaEntry(user.getAREANAME());
			InstallLightBoxList installlist=areaEntry.getInstalllightboxlist();
			Lightbox box=installlist.delItemByEMEI(editlockid);
			if(box!=null) {			
				result.put("data", "true");
			} 
		}
		return result;
	}
	
	
	@RequestMapping(value = "/deleteAllStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> deleteAllStatus(ModelMap map, HttpServletRequest request) {
		// 清空数据表
		Map<String, String> result = new HashMap<String, String>();		
		try {
			boolean flag = staservice.deleteAllItem();
			if(flag) {
				result.put("ret", "true"); // 执行成功
			}else {
				result.put("ret", "false"); // 执行失败
			}

		} catch (Exception e) {
			// TODO: handle exception
			result.put("ret", "false");
		}
		return result;
	}
	
	@RequestMapping(value = "/deleteAllBreakhistorylist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> deleteAllBreakhistorylist(ModelMap map, HttpServletRequest request) {
		// 清空数据表
		Map<String, String> result = new HashMap<String, String>();		
		try {
			boolean flag = breakservice.deleteAllItem();
			if(flag) {
				result.put("ret", "true"); // 执行成功
			}else {
				result.put("ret", "false"); // 执行失败
			}

		} catch (Exception e) {
			// TODO: handle exception
			result.put("ret", "false");
		}
		return result;
	}
}

