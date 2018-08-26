package org.kzcw.controller.api;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
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
	@RequestMapping(value = "/Uploadphoto", method = RequestMethod.POST)
	@ResponseBody
	public String Uploadphoto(MultipartFile file, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 开锁申请
		if (file != null) {
			byte[] photobytes = file.getBytes();
			Picdeliver deliverpic = Picdeliver.getInstance();
			deliverpic.pic = photobytes;
			return "true";
		}
		return "failed";
	}
	/*
	// 上传图片
	@RequestMapping(value = "/Uploadphoto")
	@ResponseBody
	public Map<String, Object> Uploadphoto(HttpServletRequest request, ModelMap map) {
		Map<String, Object> result = new HashMap<String, Object>();
		String path = request.getRealPath("/upload") + "/";
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
		}
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} // 设置编码
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 如果没以下两行设置的话,上传大的文件会占用很多内存，
		// 设置暂时存放的存储室,这个存储室可以和最终存储文件的目录不同
 
		factory.setRepository(dir);
		// 设置缓存的大小，当上传文件的容量超过该缓存时，直接放到暂时存储室
		factory.setSizeThreshold(1024 * 1024);
		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> list = upload.parseRequest((RequestContext) request);
			FileItem picture = null;
			for (FileItem item : list) {
				// 获取表单的属性名字
				String name = item.getFieldName();
				// 如果获取的表单信息是普通的 文本 信息
				if (item.isFormField()) {
					// 获取用户具体输入的字符串
					String value = item.getString();
					request.setAttribute(name, value);
				} else {
					picture = item;
				}
			}
			// 自定义上传图片的名字为userId.jpg
			String fileName = request.getAttribute("userId") + ".jpg";
			String destPath = path + fileName;
			// 真正写到磁盘上
			File file = new File(destPath);
			OutputStream out = new FileOutputStream(file);
			InputStream in = picture.getInputStream();
			int length = 0;
			byte[] buf = new byte[1024];
			// in.read(buf) 每次读到的数据存放在buf 数组中
			while ((length = in.read(buf)) != -1) {
				// 在buf数组中取出数据写到（输出流）磁盘上
				out.write(buf, 0, length);
			}
			in.close();
			out.close();
		} catch (FileUploadException e1) {
		} catch (Exception e) {
		}
		return result;
	}
   */
	@RequestMapping(value = "/installsubmit", method = RequestMethod.POST)
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
					if (areaentry != null) {
						InstallLightBoxList checkLightBoxList = areaentry.getInstalllightboxlist();
						Lightbox box = new Lightbox();
						// 获取到的emei格式为 IEME:MMMMM SN:NNNN getEMEI为只提取MMMM的内容
						box.setNAME(NAME); // 设置广交箱名
						box.setAREANAME(AREA); // 设置区域名字
						box.setIEME(getEMEI(IMEI));
						box.setLOCATION(LOCATION);
					    checkLightBoxList.addItem(box);
						result.put("data", "提交审核成功,请耐性等待");
					}else {
						result.put("data", AREA+"地区没有订阅,请先订阅该地区");
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