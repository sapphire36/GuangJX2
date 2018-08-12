package org.kzcw.service;
import java.util.List;
import org.kzcw.core.BaseServiceImpl;
import org.kzcw.dao.UserDao;
import org.kzcw.model.Area;
import org.kzcw.model.Lightbox;
import org.kzcw.model.User;
import org.kzcw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("userService")
@Component
public class UserService extends BaseServiceImpl<User> {
	private UserDao dao;
	
	@Autowired
	LightboxService lightboxservice;
	
	@Autowired
	AreaService areaservice;

	@Autowired
	@Qualifier("userDao")
	public void setuserDao(UserDao dao) {
		this.dao = dao;
	}

	public int doLogin(String uname, String passwd, long type) {
		// 返回值 0:用户名或密码错误,1:登录成功 2:用户被禁用
		User user = dao.getUser(uname, passwd, type);
		if (user != null) {
			if (user.getSTATUS() == 0) {
				return 2;
			} else {
				return 1;
			}
		} else {
			return 0;
		}
	}

	public User getUser(String uname, String passwd, long type) {
		return dao.getUser(uname, passwd, type);
	}
	
	public List<Lightbox> getLightBoxList(long uid){
		//根据用户ID来查找该用户下的光交箱列表
		User user = findById(uid);
		if(user!=null) {
			return lightboxservice.findByAreaName(user.getAREANAME());
		}
		return null;
	}
	
	public Area getArea(long uid) {
		User user = findById(uid);
		if(user!=null) {
			return areaservice.getAreaByName(user.getAREANAME());
		}
		return null;
	}
}
