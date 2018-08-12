package org.kzcw.common;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.kzcw.common.Iot.youren.ClientAdapter;
import org.kzcw.common.Iot.youren.ICallbackAdapter;
import org.kzcw.common.global.Constant;

public class YouRenManger {
	// 有人物联网控制端
	public static byte[] opendata = { (byte) 0xA5, 0x06, 0x00, 0x20, 0x00, 0x01, 0x50, (byte) 0xE4 };
	public static byte[] closedata = { (byte) 0xA5, 0x06, 0x00, 0x21, 0x00, 0x01, 0x01, 0x24 };
	private ClientAdapter clientAdapter;
	private ICallbackAdapter clinetCallbackAdapter;
	private String username;
	private String passwd;
	private AreaEntry areaEntry;

	public YouRenManger(AreaEntry session, String username, String passwd) {
		this.username = username;
		this.passwd = passwd;
		this.areaEntry = session;
	}

	public void doStart() {
		try {
			doConnect();
			areaEntry.IsWorked=true;
		} catch (MqttException e) {
			areaEntry.getSysloglist().addLog(e.toString(),Constant.LOG_ERROR);
			e.printStackTrace();
			areaEntry.IsWorked=false;
		}
	}

	public void doStop() throws MqttException {
		// 断开连接
		if (clientAdapter != null) {
			clientAdapter.DisSubscribeforuName();
			clientAdapter.DisConnectUnCheck();
		}
		if (areaEntry != null) {
			areaEntry.getParsingqueue().StopParsing(); // 结束解析
		}
	}

	public boolean doOpenLock(String emei) {
		// 打开锁
		try {
			clientAdapter.publishForDevId(emei, opendata);
			return true;
		} catch (MqttException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean doCloseLock(String emei) {
		try {
			clientAdapter.publishForDevId(emei, closedata);
			return true;
		} catch (MqttException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void doConnect() throws MqttException {
		clientAdapter = new ClientAdapter();
		clinetCallbackAdapter = new ICallbackAdapter(areaEntry);
		clientAdapter.setUsrCloudMqttCallback(clinetCallbackAdapter);
		/* 4.进行连接 */
		clientAdapter.Connect(username, passwd);
		int dotry = 2;
		while (dotry > 0) {
			if (clinetCallbackAdapter.status) {
				break;
			}
			dotry--;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// 订阅所有用户
		clientAdapter.SubscribeForUsername();// 订阅用户下所有设备
		areaEntry.getParsingqueue().StartParsing(); // 开始解析
	}
}
