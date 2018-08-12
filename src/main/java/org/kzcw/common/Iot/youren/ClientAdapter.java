package org.kzcw.common.Iot.youren;
import org.eclipse.paho.client.mqttv3.MqttException;

import cn.usr.UsrCloudMqttClientAdapter;

public class ClientAdapter extends UsrCloudMqttClientAdapter {
    //平台客户端连接程序
	@Override
	public void Connect(String userName, String passWord) throws MqttException {
		super.Connect(userName, passWord);
	}
}