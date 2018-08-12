package org.kzcw.controller.manage;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.kzcw.service.LightboxService;
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
	
	@RequestMapping(value = "/getview/getmap", method = RequestMethod.GET)
	public String getmap(ModelMap map,HttpServletRequest request){
		
		String sql = "SELECT li.ID,li.LOCATION,li.`NAME`,s.IEME,s.LOCKSTATUS,s.UNLOCKSTATUS,s.DOORSTATUS,s.TEMPERATURE,s.VOLTAGE FROM t_status s,t_lightbox li WHERE s.ID IN(select MAX(t_status.ID) from t_status GROUP BY t_status.IEME) AND li.IEME=s.IEME";
		List<Map> result= lservice.findMapByExecSQL(sql);
		//返回地图
		map.addAttribute("llist",result);
		return "/monitor/map";
	}
}
