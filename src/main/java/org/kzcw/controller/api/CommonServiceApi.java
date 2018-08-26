package org.kzcw.controller.api;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.kzcw.model.Organization;
import org.kzcw.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/common")
public class CommonServiceApi {
	//通用服务API
	
	@Autowired
	OrganizationService organizationservice;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loginin(ModelMap map, HttpServletRequest request) {
		// 登陆
		Map<String, Object> result = new HashMap<String, Object>();
		String NAME = request.getParameter("username");
		String PASSWD = request.getParameter("passwd");
		String UTYPE = request.getParameter("utype");
		// 返回值 0:用户名或密码错误,1:登录成功 2:用户被禁用
		int flag = organizationservice.doLogin(NAME, PASSWD, Integer.valueOf(UTYPE));
		Organization org = organizationservice.getOrganization(NAME, PASSWD, Integer.valueOf(UTYPE));
		result.put("data", flag);
		result.put("content", org);
		return result;
	}
}
