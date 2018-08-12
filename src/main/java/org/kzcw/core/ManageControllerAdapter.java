package org.kzcw.core;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kzcw.common.global.Constant;
import org.kzcw.model.Module;
import org.kzcw.model.Role;
import org.kzcw.model.User;
import org.kzcw.service.ModuleService;
import org.kzcw.service.RoleService;
import org.kzcw.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ManageControllerAdapter extends HandlerInterceptorAdapter implements InitializingBean {
	// HandlerInterceptor 接口中定义了三个方法，我们就是通过这三个方法来对用户的请求进行拦截处理的。
	@Autowired
	private ModuleService moduleService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userservice;

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
	}

	/**
	 * 该方法将在请求处理之前进行调用。SpringMVC 中的Interceptor 是链式的调用的，
	 * 在一个应用中或者说是在一个请求中可以同时存在多个Interceptor 。每个Interceptor
	 * 的调用会依据它的声明顺序依次执行，而且最先执行的都是Interceptor 中的preHandle 方法，
	 * 所以可以在这个方法中进行一些前置初始化操作或者是对当前请求的一个预处理，也可以在这个方法
	 * 中进行一些判断来决定请求是否要继续进行下去。该方法的返回值是布尔值Boolean类型的，当它返 回为false
	 * 时，表示请求结束，后续的Interceptor 和Controller 都不会再执行；当返回值为 true 时就会继续调用下一个Interceptor
	 * 的preHandle 方法，如果已经是最后一个Interceptor 的时候就会是调用当前请求的Controller 方法。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object userid = request.getSession().getAttribute(Constant.USERID);
		String path = request.getContextPath();
		if (path.isEmpty()) {
			path = "/";
		} else {
			path = path + "/login/index";
		}
		if (userid == null) {
			response.sendRedirect(path);
			return false;
		} else {
			/*
			SystemData systemdata = SystemData.getInstance();
			UserSession usersession = systemdata.getUserSession((String) userid);
			if (usersession != null) {
				if (usersession.getJsessionId().equals(request.getSession().getId())) {
					return true;
				}
			}
			response.sendRedirect(path);
			return false;
			*/
			return true;
		}
	}

	/**
	 * 由preHandle 方法的解释我们知道这个方法包括后面要说到的afterCompletion 方法都只能是 在当前所属的Interceptor
	 * 的preHandle 方法的返回值为true 时才能被调用。postHandle 方法，顾名思义就是在当前请求进行处理之后，也就是Controller
	 * 方法调用之后执行，但是它会 在DispatcherServlet 进行视图返回渲染之前被调用，所以我们可以在这个方法中对Controller
	 * 处理之后的ModelAndView 对象进行操作。postHandle 方法被调用的方向跟preHandle 是相反
	 * 的，也就是说先声明的Interceptor 的postHandle 方法反而会后执行，这和Struts2 里面的 Interceptor
	 * 的执行过程有点类型。Struts2 里面的Interceptor 的执行过程也是链式的，只是 在Struts2
	 * 里面需要手动调用ActionInvocation 的invoke 方法来触发对下一个Interceptor 或者是Action
	 * 的调用，然后每一个Interceptor 中在invoke 方法调用之前的内容都是按照声明顺 序执行的，而invoke 方法之后的内容就是反向的。
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj,
			ModelAndView modelAndView) throws Exception {
		try {
			Object userid = request.getSession().getAttribute(Constant.USERID);
			if (userid == null)
				return;
			User user = userservice.findById(Long.parseLong((String) userid));
			if (user == null)
				return;
			List<Module> result = new ArrayList<Module>();
			String rights = "";
			Role role = roleService.findById(user.getROLEID());
			if (role == null)
				return;
			rights = role.getRIGHTS();
			if (rights == null)
				return;
			List<Module> mlist = moduleService.list();
			if (!rights.equals("")) {
				String[] tt = rights.split(",");// 分割获取模块列表
				for (Module m : mlist) {
					if (IsInRight(m.getCODE(), tt)) {
						// 如果存在,则添加到返回列表中
						result.add(m);
					}
				}
			}
			if (modelAndView != null) {
				modelAndView.addObject("list", result);
			}
		} catch (Exception e) {
		}
	}

	private boolean IsInRight(long id, String[] ll) {
		// 判断是否在列表中
		String code = String.valueOf(id);
		for (int i = 0; i < ll.length; i++) {
			if (code.equals(ll[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。
	 * 该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行，
	 * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的 preHandle方法的返回值为true时才会执行。
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}
}
