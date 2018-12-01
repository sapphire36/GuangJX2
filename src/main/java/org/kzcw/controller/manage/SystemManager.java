package org.kzcw.controller.manage;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.kzcw.common.global.Constant;
import org.kzcw.service.SystemlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manage/system")
public class SystemManager {
    //系统设置管理
	@Autowired
	SystemlogsService systemservice;

	// ************************************系统参数设置**************************
	// ************************************系统参数设置**************************
	@RequestMapping(value = "/getview/setting", method = RequestMethod.GET)
	public String indexview(ModelMap map,HttpServletRequest request){
		return "/system/setting";
	}
	
	@RequestMapping(value = "getview/warntriggerset", method = RequestMethod.GET)
	public String warntriggerset(ModelMap map,HttpServletRequest request){
		map.addAttribute("max_temprature", Constant.MAX_TEMRATURE);
		map.addAttribute("min_temprature", Constant.MIN_TEMRATURE);
		map.addAttribute("max_volume", Constant.MAX_VOLUME);
		map.addAttribute("min_volume", Constant.MIN_VOLUME);
		return "/system/warntrigger";
	}
	
	@RequestMapping(value = "getview/savewarntriggerset", method = RequestMethod.POST)
	public String savewarntriggerset(ModelMap map,HttpServletRequest request){
		String max_temprature = request.getParameter("max_temprature");
		String min_temprature = request.getParameter("min_temprature");
		String max_volume = request.getParameter("max_volume");
		String min_volume = request.getParameter("min_volume");
		Constant.setMAX_TEMRATURE(Float.valueOf(max_temprature));
		Constant.setMIN_TEMRATURE(Float.valueOf(min_temprature));
		Constant.setMAX_VOLUME(Float.valueOf(max_volume));
		Constant.setMIN_VOLUME(Float.valueOf(min_volume));
		map.addAttribute("max_temprature", Constant.MAX_TEMRATURE);
		map.addAttribute("min_temprature", Constant.MIN_TEMRATURE);
		map.addAttribute("max_volume", Constant.MAX_VOLUME);
		map.addAttribute("min_volume", Constant.MIN_VOLUME);
		return "/system/warntrigger";
	}
	
    @RequestMapping(value="/restart",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> restart(ModelMap map,HttpServletRequest request){
		//重启监听
    	Map<String,String> result=new HashMap<String,String>();
       /* YouRenManger manager=YouRenManger.getInstance();
        String isALL=request.getParameter("ISALL");
        try {
            new Thread("线程1"){
                @Override
                public void run(){
                	if(isALL.equals("true")) {
                		//订阅该用户下所有设备
                		manager.doStart(true);
                	}else {
                		//只订阅已注册的设备
                		manager.doStart(false);
                	}
                }
            }.start();
        	result.put("data","true");
		} catch (Exception e) {
			// TODO: handle exception
			result.put("data","false");
		}
		*/
        return result;
    }
    
    @RequestMapping(value="/stop",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> stop(ModelMap map,HttpServletRequest request){
		//关闭监听
    	
    	Map<String,String> result=new HashMap<String,String>();
       /* YouRenManger manager=YouRenManger.getInstance();
    	try {
			manager.doDisConnect();
		   	result.put("data","true");
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("data","false");
		}
		*/
        return result;
    }


	//************************************系统日志**************************
	// ************************************系统日志**************************
	@RequestMapping(value = "/getview/journallist", method = RequestMethod.GET)
	public String onstructlist(ModelMap map,HttpServletRequest request){
		//获取系统日志
		map.addAttribute("journallist",systemservice.list());
		return "system/journallist";
	}
	
	@RequestMapping(value = "/deletealljournal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> deletealljournal(ModelMap map, HttpServletRequest request) {
		// 清空数据表
		Map<String, String> result = new HashMap<String, String>();		
		try {
			boolean flag = systemservice.deleteAll();
			if(flag) {
				result.put("ret", "true"); // 执行成功
			}else {
				result.put("ret", "false"); // 执行失败
			}

		} catch (Exception e) {
			// TODO: handle exception
			result.put("ret", "false");
		}
		return result;
	}
}
