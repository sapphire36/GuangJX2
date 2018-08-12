
package org.kzcw.controller.api;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.kzcw.common.AreaEntry;
import org.kzcw.common.Iot.utils.OperateMessage;
import org.kzcw.common.global.SystemData;
import org.kzcw.model.Lightbox;
import org.kzcw.service.LightboxService;
import org.kzcw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/weixinapi")
public class ServiceInterface {

	@Autowired
	UserService userservice;
	
	@Autowired
	LightboxService lightboxservice;

	@RequestMapping(value = "/operateLock", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> operateLock(ModelMap map, @RequestParam String UNAME, @RequestParam String EMEI,
			@RequestParam int TYPE,HttpServletRequest request) {
		// 开锁申请
		Map<String, Object> result = new HashMap<String, Object>();
		String emei = getEMEI(EMEI);
		Lightbox lightbox = lightboxservice.findByIMEI(emei);
		SystemData systemdata = SystemData.getInstance();
		AreaEntry areaentry = systemdata.getAreaEntry(lightbox.getAREANAME());
		OperateMessage message ;
		if(TYPE==1) {
			message = new OperateMessage(UNAME, EMEI, true);
		}else {
			message = new OperateMessage(UNAME, EMEI, false);
		}
		// message;
		message.USERNAME = UNAME;
		message.time = new Date().toString();
		message.EMEI = emei;
		areaentry.getOperateList().addOperateItem(message);
		result.put("data", "申请成功,请耐心等待审核开锁..");
		return result;
	}

	// 上传图片
	@RequestMapping(value = "/Uploadphoto",method=RequestMethod.POST)
	@ResponseBody
	public String Uploadphoto(MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException {
		// 开锁申请
		if(file!=null) {
			byte[] photobytes=file.getBytes();
		/*	Picdeliver deliverpic=Picdeliver.getInstance();
			deliverpic.pic=photobytes;
			CheckLightBoxList checkLightBoxList=CheckLightBoxList.getInstance();
			Lightbox box=new Lightbox();
			String coderesult=request.getParameter("coderesult");
			String locationresult=request.getParameter("locationresult");
			String name=request.getParameter("name");
			
			if((name!=null)&&(locationresult!=null)&&(coderesult!=null)) {
				if((!name.isEmpty())&&(!locationresult.isEmpty())&&(!coderesult.isEmpty())) {
				String ieme=getEMEI(coderesult);
				box.setNAME(name);
				box.setAREANAME(name);
				box.setIEME(ieme);
				box.setLOCATION(locationresult);
				checkLightBoxList.addItem(box);
				return"提交审核成功,请耐心等待";
				}else {
					return "提交数据格式不能为空";
				}
			}
			*/
			return "success";
		}else {
			return "failed";
		}
	}

	@RequestMapping(value = "/loginin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loginin(ModelMap map, HttpServletRequest request) {
		// 登陆
		Map<String, Object> result = new HashMap<String, Object>();
		String NAME = request.getParameter("username");
		String PASSWD = request.getParameter("passwd");
		String UTYPE = request.getParameter("utype");
		// 返回值 0:用户名或密码错误,1:登录成功 2:用户被禁用
		int flag = userservice.doLogin(NAME, PASSWD, Long.parseLong(UTYPE));

		result.put("data", flag);
		return result;
	}
	
	@RequestMapping(value = "/installsubmit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> installsubmit(ModelMap map, HttpServletRequest request) {
		// 登陆
		Map<String, Object> result = new HashMap<String, Object>();
		String coderesult = request.getParameter("coderesult");
		String locationresult = request.getParameter("locationresult");
		String name = request.getParameter("name");
		
//		Lightbox box=new Lightbox();
//		box.setNAME(name);
//		box.setIEME(coderesult);
//		box.setLOCATION(locationresult);
//		
//  	CheckLightBoxList lightboxlist=CheckLightBoxList.getInstance();
//		lightboxlist.list.add(box);
		
		// 返回值 0:用户名或密码错误,1:登录成功 2:用户被禁用
		boolean codestart=coderesult.startsWith("完成扫码");
		boolean locationstart=locationresult.startsWith("纬度");
		if(codestart==true&&locationstart==true&&name.isEmpty()==false)
		result.put("data", 0);
		else result.put("data", 1);
			
		return result;
	}
	
	@RequestMapping(value = "/checkLock", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkLock(ModelMap map,HttpServletRequest request) {
		// 审核申请
		Map<String, Object> result = new HashMap<String, Object>();
		//单例模式实例化
/*		try {
			//使用异常处理,提高程序稳健型
			String coderesult = request.getParameter("coderesult");
			String locationresult = request.getParameter("locationresult");
			String name = request.getParameter("name");
			
			if((name!=null)&&(locationresult!=null)&&(coderesult!=null)) {
				//首先判断是否为null,否则假如为null,则执行isEmpty()方法会报NullException错误
				if((!name.isEmpty())&&(!locationresult.isEmpty())&&(!coderesult.isEmpty())) {
					//再判断是否为空字符串
					CheckLightBoxList checklist = CheckLightBoxList.getInstance();
					Lightbox box=new Lightbox();
					//获取到的emei格式为 IEME:MMMMM SN:NNNN getEMEI为只提取MMMM的内容
					String ieme = getEMEI(coderesult);
					box.setNAME(name); //设置广交箱名	
					box.setAREANAME(name); //设置区域名字
					box.setIEME(ieme);
					box.setLOCATION(locationresult);
					checklist.addItem(box);
					result.put("data","提交审核成功,请耐性等待");
				}else {
					result.put("data","提交数据格式不能为空");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			//发生异常处理
			result.put("data",e.getMessage());//返回异常的原因 e.getMessage为异常原因
		}
		*/
	   return result;
	}
		/*
		Map<String, Object> result = new HashMap<String, Object>();
		CheckLightBoxList checklist = new CheckLightBoxList();
		CheckMessage message = new CheckMessage(NAME, IEME ,LOCATION);
		// message;
		//String ieme = getEMEI(IEME);
		message.NAME = NAME;
		message.IEME = IEME;
		message.LOCATION = LOCATION;
		checklist.AddItem(message);
		CheckLightBoxList.setinstance(checklist);
		result.put("data", "申请成功,请耐心等待审核开锁..");
		return result;
		*/
		/*
		if(name.isEmpty()==false&&coderesult.isEmpty()==false&&locationresult.isEmpty()==false)
			result.put("data","提交审核成功");
			else result.put("data","提交审核失败");	
	    */
	public String getEMEI(String tg) {
		tg = tg.replaceAll(" ", "");
		int i = tg.indexOf("I:");
		int j = tg.indexOf("SN");
		return tg.substring(i + 2, j);
	}
}