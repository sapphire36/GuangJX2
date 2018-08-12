package org.kzcw.controller.api;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.kzcw.common.AreaEntry;
import org.kzcw.common.InstallLightBoxList;
import org.kzcw.common.OperateList;
import org.kzcw.common.Iot.utils.OperateMessage;
import org.kzcw.common.global.Picdeliver;
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
@RequestMapping("/service")
public class WeiXinAppApi {
	@Autowired
	UserService userservice;

	@Autowired
	LightboxService lightboxservice;

	@RequestMapping(value = "/openLock", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> openLock(ModelMap map, @RequestParam String UNAME, @RequestParam String EMEI,
			HttpServletRequest request) {
		// 开锁申请
		Map<String, Object> result = new HashMap<String, Object>();
		OperateMessage message = new OperateMessage(UNAME, EMEI,true);
		SystemData systemdata = SystemData.getInstance();
		AreaEntry areaentry = systemdata.getAreaEntry("XIAN");
		OperateList operateList = null;
		if (areaentry != null) {
			operateList = areaentry.getOperateList();
		}
		// message;
		String emei = getEMEI(EMEI);
		message.USERNAME = UNAME;
		message.time = new Date().toString();
		message.EMEI = emei;
		if(operateList!=null) {
			operateList.addOperateItem(message);
			result.put("data", "关锁成功..");
		}else {
			result.put("data", "XIAN地区没有订阅,请先订阅该地区");
		}
		result.put("data", "关锁成功..");
		return result;
	}

	@RequestMapping(value = "/closeLock", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> closeLock(ModelMap map, @RequestParam String UNAME, @RequestParam String EMEI,
			HttpServletRequest request) {
		// 关锁申请
		Map<String, Object> result = new HashMap<String, Object>();
		OperateMessage message = new OperateMessage(UNAME, EMEI,false);
		SystemData systemdata = SystemData.getInstance();
		AreaEntry areaentry = systemdata.getAreaEntry("XIAN");
		OperateList operateList = null;
		if (areaentry != null) {
			operateList = areaentry.getOperateList();
		}
		// message;
		String emei = getEMEI(EMEI);
		message.USERNAME = UNAME;
		message.time = new Date().toString();
		message.EMEI = emei;
		if(operateList!=null) {
			operateList.addOperateItem(message);
			result.put("data", "关锁成功..");
		}else {
			result.put("data", "XIAN地区没有订阅,请先订阅该地区");
		}
		result.put("data", "关锁成功..");
		return result;
	}

	// 上传图片
	@RequestMapping(value = "/Uploadphoto", method = RequestMethod.POST)
	@ResponseBody
	public String Uploadphoto(MultipartFile file, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 开锁申请
		if (file != null) {
			byte[] photobytes = file.getBytes();
			Picdeliver deliverpic = Picdeliver.getInstance();
			deliverpic.pic = photobytes;

			Lightbox box = new Lightbox();
			String coderesult = request.getParameter("coderesult");
			String locationresult = request.getParameter("locationresult");
			String name = request.getParameter("name");

			String emei = getEMEI(coderesult);
			SystemData systemdata = SystemData.getInstance();
			AreaEntry areaentry = systemdata.getAreaEntry("XIAN");
			InstallLightBoxList checkLightBoxList = null;
			if (areaentry != null) {
				checkLightBoxList = areaentry.getInstalllightboxlist();
			}
			if ((name != null) && (locationresult != null) && (coderesult != null)) {
				if ((!name.isEmpty()) && (!locationresult.isEmpty()) && (!coderesult.isEmpty())) {
					box.setNAME(name);
					box.setAREANAME(name);
					box.setIEME(emei);
					box.setLOCATION(locationresult);
					if (checkLightBoxList != null) {
						checkLightBoxList.addItem(box);
						return "提交审核成功,请耐心等待";
					} else {
						return "XIAN地区没有订阅,请先订阅该地区";
					}
				} else {
					return "提交数据格式不能为空";
				}
			} 
		}
		return "failed";
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
		boolean codestart = coderesult.startsWith("完成扫码");
		boolean locationstart = locationresult.startsWith("纬度");
		if (codestart == true && locationstart == true && name.isEmpty() == false)
			result.put("data", 0);
		else
			result.put("data", 1);

		return result;
	}

	@RequestMapping(value = "/checkLock", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkLock(ModelMap map, HttpServletRequest request) {
		// 审核申请
		Map<String, Object> result = new HashMap<String, Object>();
		// 单例模式实例化
		try {
			// 使用异常处理,提高程序稳健型
			String coderesult = request.getParameter("coderesult");
			String locationresult = request.getParameter("locationresult");
			String name = request.getParameter("name");

			if ((name != null) && (locationresult != null) && (coderesult != null)) {
				// 首先判断是否为null,否则假如为null,则执行isEmpty()方法会报NullException错误
				if ((!name.isEmpty()) && (!locationresult.isEmpty()) && (!coderesult.isEmpty())) {
					// 再判断是否为空字符串
					SystemData systemdata = SystemData.getInstance();
					AreaEntry areaentry = systemdata.getAreaEntry("XIAN");
					InstallLightBoxList checkLightBoxList = null;
					if (areaentry != null) {
						checkLightBoxList = areaentry.getInstalllightboxlist();
					}
					Lightbox box = new Lightbox();
					// 获取到的emei格式为 IEME:MMMMM SN:NNNN getEMEI为只提取MMMM的内容
					String ieme = getEMEI(coderesult);
					box.setNAME(name); // 设置广交箱名
					box.setAREANAME(name); // 设置区域名字
					box.setIEME(ieme);
					box.setLOCATION(locationresult);
					if(checkLightBoxList!=null) {
						checkLightBoxList.addItem(box);
						result.put("data", "提交审核成功,请耐性等待");
					}else {
						result.put("data", "XIAN地区没有订阅,请先订阅该地区");
					}
					
				} else {
					result.put("data", "提交数据格式不能为空");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			// 发生异常处理
			result.put("data", e.getMessage());// 返回异常的原因 e.getMessage为异常原因
		}
		return result;
	}

	public String getEMEI(String tg) {
		tg = tg.replaceAll(" ", "");
		int i = tg.indexOf("I:");
		int j = tg.indexOf("SN");
		return tg.substring(i + 2, j);
	}
}
