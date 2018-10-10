package org.kzcw.controller.login;

import org.kzcw.common.AreaEntry;
import org.kzcw.common.UserSession;
import org.kzcw.common.global.Constant;
import org.kzcw.common.global.SystemData;
import org.kzcw.model.User;
import org.kzcw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {
    // 登录管理
    @Autowired
    UserService userservice;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap map, HttpServletRequest request) {

        return "/login/login";
    }

    @RequestMapping(value = "/dologin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> dologin(ModelMap map, HttpServletRequest request) {
        //判断执行登录

        Map<String, String> ret = new HashMap<String, String>();
        String name = request.getParameter("USERNAME"); //获取用户名
        String passwd = request.getParameter("PASSWD");
        String usertype = request.getParameter("USERTYPE");
        int flag = userservice.doLogin(name, passwd, Long.parseLong(usertype));
        ret.put("data", String.valueOf(flag));//设置登录结果
        if (flag == 1) {
            //设置登录session标示
            SystemData systemdata = SystemData.getInstance();
            User user = userservice.getUser(name, passwd, Long.parseLong(usertype));
            request.getSession().setAttribute(Constant.USERID, String.valueOf(user.getID()));//设置用户ID
            request.getSession().setMaxInactiveInterval(Constant.sessiontime);//设置session存放时间
            AreaEntry areaEntry = systemdata.getAreaEntry(user.getAREANAME());
            if (areaEntry != null) {
                UserSession userSession = areaEntry.getUsersession(name);
                if (userSession != null) {
                    userSession.setJsessionId(request.getSession().getId());
                }
            }
        }
        return ret;
    }
}
