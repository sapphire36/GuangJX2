package org.kzcw.common.global;
public class Constant{
	
	//在线管理员引用名
	public static final String OnlineUserManageFlag = "OnlineUserManageFlag";
 
	//新建用户默认密码
	public static final String DefaultPassword = "1";
	
	public static final String USERNAME = "USERNAME";
	public static final String USERTYPE = "USERTYPE";
	public static final String USERID = "USERID";
	public static final int LOG_ERROR = 3;
	public static final int LOG_WARN = 2;
	public static final int LOG_MESSAGE = 1;
	public static final int sessiontime = 3600; //session存留时间
	public static float MIN_VOLUME=3.1f;  //最小电压
	public static float MAX_VOLUME=5.0f;  //最大电压
	public static float MIN_TEMRATURE=-30f; //最小温度
	public static float MAX_TEMRATURE=85f; //最大温度
	
	public static final int WARN_LOW_TEMRATURE=1000; //温度过低
	public static final int WARN_HIGH_TEMRATURE=1001; //温度过高
	public static final int WARN_LOW_VOLUME=1002; //电压过低
	public static final int WARN_HIGH_VOLUME=1003; //电压过高
	public static final int WARN_ILLEGAL_OPENDOOR=1004; //非法开锁
	public static final int WARN_MECHAN_BREAKDOWN=1005; //机械故障
	public static final int WARN_NORMAL=1006; //正常
	public static final int WARN_UNKOWN=1007; //未知
	
	public static float getMIN_VOLUME() {
		return MIN_VOLUME;
	}
	public static void setMIN_VOLUME(float mIN_VOLUME) {
		MIN_VOLUME = mIN_VOLUME;
	}
	public static float getMAX_VOLUME() {
		return MAX_VOLUME;
	}
	public static void setMAX_VOLUME(float mAX_VOLUME) {
		MAX_VOLUME = mAX_VOLUME;
	}
	public static float getMIN_TEMRATURE() {
		return MIN_TEMRATURE;
	}
	public static void setMIN_TEMRATURE(float mIN_TEMRATURE) {
		MIN_TEMRATURE = mIN_TEMRATURE;
	}
	public static float getMAX_TEMRATURE() {
		return MAX_TEMRATURE;
	}
	public static void setMAX_TEMRATURE(float mAX_TEMRATURE) {
		MAX_TEMRATURE = mAX_TEMRATURE;
	}
}
