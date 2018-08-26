package org.kzcw.controller.manage;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.kzcw.common.global.Constant;
import org.kzcw.model.Lightbox;
import org.kzcw.model.User;
import org.kzcw.service.LightboxService;
import org.kzcw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/manage/monitor")
public class MonitorManager {
       //监控中心
	
	@Autowired
	LightboxService lservice; //箱体信息类
	
	@Autowired
	private UserService userservice;
	
	@RequestMapping(value = "/getview/getmap", method = RequestMethod.GET)
	public String getmap(ModelMap model,HttpServletRequest request){
		//返回地图
		try {
			Object userid = request.getSession().getAttribute(Constant.USERID);
			if (userid!= null) {
				User user = userservice.findById(Long.parseLong((String) userid));
				if(user!=null) {
					List<Lightbox> llist=lservice.getList(user.getAREANAME());
					model.addAttribute("lightboxlist", llist);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "/monitor/map";
		//String sql = "SELECT li.ID,li.LOCATION,li.`NAME`,s.IEME,s.LOCKSTATUS,s.UNLOCKSTATUS,s.DOORSTATUS,s.TEMPERATURE,s.VOLTAGE FROM t_status s,t_lightbox li WHERE s.ID IN(select MAX(t_status.ID) from t_status GROUP BY t_status.IEME) AND li.IEME=s.IEME";
		//List<Map> result= lservice.findMapByExecSQL(sql);
		//System.out.println(result);
		//model.addAttribute("llist",result);
	}
}
