package org.kzcw.controller.api;

import org.kzcw.model.Status;
import org.kzcw.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/thermal")
public class ThermalServiceApi {
    //供热系统服务API

    @Autowired
    StatusService statusService;

    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getData(HttpServletRequest request) {
        // 登陆
        Map<String, Object> result = new HashMap<String, Object>();
        String IMEI = request.getParameter("IMEI");
        if (IMEI == null) {
            result.put("result", "false");
            result.put("content", "IMEI不能为空！");
        }
        Status status = statusService.getRecentRecord(IMEI);
        if (status == null) {
            result.put("result", "true");
            result.put("content", "没有找到IMEI为" + IMEI + "的设备信息");
        } else {
            result.put("result", "true");
            result.put("content", status);
        }
        return result;
    }
}
