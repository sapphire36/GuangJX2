package org.kzcw.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.kzcw.common.AreaEntry;
import org.kzcw.common.OperateList;
import org.kzcw.common.Iot.utils.OperateMessage;
import org.kzcw.common.global.SystemData;
import org.kzcw.model.Lightbox;
import org.kzcw.service.LightboxService;
import org.kzcw.service.OperatehistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/operate")
public class OperateServiceApi {
    // 施工人员API

    @Autowired
    OperatehistoryService operatehistoryservice; // 操作历史

    @Autowired
    LightboxService lightboxservice; // 光交箱

    @RequestMapping(value = "/openLightbox", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> openLock(ModelMap map, HttpServletRequest request) {
        // 开箱申请
        Map<String, Object> result = new HashMap<String, Object>();
        String UNAME = request.getParameter("UNAME");// 用户名字
        String IMEI = request.getParameter("IMEI");// 设备IMEI码
        String AREA = request.getParameter("AREA");// 地区
        if ((UNAME == null) || (IMEI == null) || (AREA == null)) {
            result.put("data", "请检查是否获取设备IMEI码或者是否选择地区!");
            return result;
        }
        SystemData systemdata = SystemData.getInstance();
        AreaEntry areaentry = systemdata.getAreaEntry(AREA);
        if (areaentry != null) {
            OperateMessage message = new OperateMessage(UNAME, getEMEI(IMEI), true); // 开锁
            OperateList operateList = areaentry.getOperateList();
            operateList.addOperateItem(message);
            result.put("data", "提交申请成功,请耐性等待开锁,时间大概1分钟左右");
        } else {
            result.put("data", AREA + "地区没有订阅,请联系管理员先订阅该地区");
        }
        return result;
    }

    @RequestMapping(value = "/getOpenStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getOpenStatus(ModelMap map, HttpServletRequest request) {
        // 检查开锁状态
        Map<String, Object> result = new HashMap<String, Object>();
        String UNAME = request.getParameter("UNAME");// 用户名字
        String IMEI = request.getParameter("IMEI");// 设备IMEI码
        String AREA = request.getParameter("AREA");// 地区
        if ((UNAME == null) || (IMEI == null) || (AREA == null)) {
            result.put("data", "请检查是否获取设备IMEI码或者是否选择地区!");
            return result;
        }
        OperateMessage message = new OperateMessage(UNAME, getEMEI(IMEI), true); // 开锁
        SystemData systemdata = SystemData.getInstance();
        AreaEntry areaentry = systemdata.getAreaEntry(AREA);
        OperateList operateList = null;
        if (areaentry != null) {
            operateList = areaentry.getOperateList();
        }
        if (operateList != null) {
            operateList.addOperateItem(message);
            result.put("data", "true");
            result.put("content", "提交申请成功,请耐性等待开锁,时间大概1分钟左右");
        } else {
            result.put("data", "false");
            result.put("content", AREA + "地区没有订阅,请联系管理员先订阅该地区");
        }
        return result;
    }

    @RequestMapping(value = "/closeLightbox", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> closeLightbox(ModelMap map, HttpServletRequest request) {
        // 关箱申请
        Map<String, Object> result = new HashMap<String, Object>();
        String UNAME = request.getParameter("UNAME");// 用户名字
        String IMEI = request.getParameter("IMEI");// 设备IMEI码
        if ((UNAME == null) || (IMEI == null)) {
            result.put("data", "请检查是否获取设备IMEI码或者是否选择地区!");
            return result;
        }
        try {
            Lightbox lightbox = lightboxservice.findByIMEI(getEMEI(IMEI));
            if (lightbox != null) {
                lightbox.setCONSTRUCTSTATUS(0);// 设置施工状态为0
                lightboxservice.update(lightbox);
                result.put("data", "提交成功!");
            } else {
                result.put("data", "没有找到IMEI为:" + IMEI + "的设备,请检查IMEI是否有误或请联系安装人员");
            }
        } catch (Exception e) {
            result.put("data", e.getMessage());
        }
        OperateMessage message = new OperateMessage(UNAME, getEMEI(IMEI), false); // 开锁
        SystemData systemdata = SystemData.getInstance();
        AreaEntry areaentry = systemdata.getAreaEntry("XIANTEST");
        OperateList operateList = null;
        if (areaentry != null) {
            operateList = areaentry.getOperateList();
        }
        if (operateList != null) {
            operateList.addOperateItem(message);
        }
        return result;
    }

    @RequestMapping(value = "/getLightboxList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getLightboxList(ModelMap map, HttpServletRequest request) {
        // 登陆
        Map<String, Object> result = new HashMap<String, Object>();
        String AREA = request.getParameter("AREA");
        try {
            if (AREA != null) {
                List<Lightbox> lightboxlist = lightboxservice.getList(AREA);
                result.put("data", "true");
                result.put("content", lightboxlist);
            } else {
                result.put("data", "false");
                result.put("content", "not found");
            }
        } catch (Exception e) {
            result.put("data", "false");
            result.put("content", e.getMessage());
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
