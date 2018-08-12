package org.kzcw.common.Iot.youren;

import org.kzcw.common.ParsingReceiveQueue;
import org.kzcw.common.SysLogList;
import org.kzcw.common.global.Constant;
import org.kzcw.common.AreaEntry;

import cn.usr.UsrCloudMqttCallbackAdapter;

public class ICallbackAdapter extends UsrCloudMqttCallbackAdapter {

	public boolean status = false;
	ParsingReceiveQueue parsingmanager;
	SysLogList logmanager;

	public ICallbackAdapter(AreaEntry session) {
		parsingmanager = session.getParsingqueue();
		logmanager=session.getSysloglist();
	}

	@Override
	public void onConnectAck(int returnCode, String description) {
		super.onConnectAck(returnCode, description);
		logmanager.addLog("正在初始化服务器" + returnCode, Constant.LOG_MESSAGE);// 添加日志
		if (returnCode == 2) {
			logmanager.addLog("连接成功!" + returnCode, Constant.LOG_MESSAGE);// 添加日志
			status = true;
		}
	}

	@Override
	public void onSubscribeAck(int messageId, String clientId, String topics, int returnCode) {
		logmanager.addLog("订阅成功!" + returnCode, 1);// 添加日志
		super.onSubscribeAck(messageId, clientId, topics, returnCode);

	}

	@Override
	public void onPublishDataAck(int messageId, String topic, boolean isSuccess) {
		// 推送确认
		super.onPublishDataAck(messageId, topic, isSuccess);
		//logmanager.addLog("推送确认" + topic, 1);// 添加日志
	}

	@Override
	public void onPublishDataResult(int messageId, String topic) {
		// 推送结果
		super.onPublishDataResult(messageId, topic);
		//logmanager.addLog("推送结果" + topic, 1);// 添加日志
	}

	@Override
	public void onReceiveEvent(int messageId, String topic, byte[] data) {
		super.onReceiveEvent(messageId, topic, data);
		// 进行解析
		parsingmanager.AddMessage(new ReceiveData(messageId, topic, data));
		//logmanager.addLog("收到上报数据" + topic, 1);// 添加日志
	}
}
