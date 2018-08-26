package org.kzcw.controller.api;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.kzcw.common.AreaEntry;
import org.kzcw.common.InstallLightBoxList;
import org.kzcw.common.global.Picdeliver;
import org.kzcw.common.global.SystemData;
import org.kzcw.model.Lightbox;
import org.kzcw.service.LightboxService;
import org.kzcw.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/install")
public class InstallServieApi {

	@Autowired
	OrganizationService organizationservice;
	
	@Autowired
	LightboxService lightboxservice; //光交箱

	// 上传图片
	@RequestMapping(value = "/Uploadphoto", method = RequestMethod.GET)
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

	@RequestMapping(value = "/installsubmit", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkLock(ModelMap map, HttpServletRequest request) {
		// 审核申请
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String IMEI = request.getParameter("IMEI");
			String LOCATION = request.getParameter("LOCATION");
			String NAME = request.getParameter("NAME");
			String AREA = request.getParameter("AREA");

			if ((NAME != null) && (LOCATION != null) && (IMEI != null)&& (AREA!=null)) {
				// 首先判断是否为null,否则假如为null,则执行isEmpty()方法会报NullException错误
				if ((!NAME.isEmpty()) && (!LOCATION.isEmpty()) && (!IMEI.isEmpty()&&(!AREA.isEmpty()))) {
					// 再判断是否为空字符串
					SystemData systemdata = SystemData.getInstance();
					AreaEntry areaentry = systemdata.getAreaEntry(AREA);
					InstallLightBoxList checkLightBoxList = null;
					if (areaentry != null) {
						checkLightBoxList = areaentry.getInstalllightboxlist();
					}
					Lightbox box = new Lightbox();
					// 获取到的emei格式为 IEME:MMMMM SN:NNNN getEMEI为只提取MMMM的内容
					box.setNAME(NAME); // 设置广交箱名
					box.setAREANAME(AREA); // 设置区域名字
					box.setIEME(getEMEI(IMEI));
					box.setLOCATION(LOCATION);
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