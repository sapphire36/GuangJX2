package org.kzcw.common.Iot;

import java.math.BigInteger;
import java.util.Date;

import org.kzcw.common.AreaEntry;
import org.kzcw.common.Iot.youren.CRC16;
import org.kzcw.common.Iot.youren.ReceiveData;
import org.kzcw.common.Iot.youren.ResultData;
import org.kzcw.model.Status;

public class HandleResult implements Runnable {
	// 处理返回结果
	public ReceiveData receivedata;
	private AreaEntry areaEntry;

	public HandleResult(AreaEntry usersession, ReceiveData receive) {
		areaEntry = usersession;
		this.receivedata = receive;
	}

	@Override
	public void run() {
		ParsingData(receivedata);// 解析
	}

	public void ParsingData(ReceiveData data) {

		String newstring = bytes2hex01(data.data);
		String subnewstring = newstring.substring(0, newstring.length() - 4);
		String subendstring = newstring.substring(newstring.length() - 4, newstring.length());
		String finalstring = CRC16.checkcode(subnewstring);
		if (subendstring.equals(finalstring) == true) {
			if (newstring.length() > 45) {
				ResultData newdevice = analysisdata(newstring);
				newdevice.DeviceID = data.topic.substring(data.topic.length() - 15, data.topic.length());
				areaEntry.getWaitpublishqueue().doOperate(newdevice.DeviceID); // 执行操作

				Status status = new Status();
				status.setIEME(newdevice.DeviceID);
				if (newdevice.eleclock) { // 设置电子锁开
					status.setLOCKSTATUS(1);
				}else {
					status.setLOCKSTATUS(0);
				}
				if (newdevice.elecunlock) { // 电子锁关状态
					status.setUNLOCKSTATUS(1);
				}else {
					status.setUNLOCKSTATUS(0);
				}
				if (newdevice.handlock) {
					// 设置电子锁开
					status.setDOORSTATUS(1);
				}else {
					status.setDOORSTATUS(0);
				}

				status.setTEMPERATURE(String.valueOf(newdevice.temperature));
				status.setVOLTAGE(String.valueOf(newdevice.volt));
				status.setADDTIME(new Date());
				status.setAREANAME(areaEntry.getArea().getAREANAME());
				areaEntry.getStatuslist().add(status); // 将status添加到缓冲队列
				areaEntry.getSysloglist().addLog("接收到数据" + newdevice.DeviceID, 1);

			}
		} else {
			areaEntry.getSysloglist().addLog("CRC校验失败!!", 1);
		}
	}

	public String getIMEIbyTopic(String topic) {
		// 获取IMEI编号
		String ret = "";
		for (int i = 0; i < topic.length(); i++) {
			if ((topic.charAt(i) > '0') && (topic.charAt(i) < '9')) {
				ret += topic.charAt(i);
			}
		}
		return ret;
	}

	public static String bytes2hex01(byte[] bytes) {
		BigInteger bigInteger = new BigInteger(1, bytes);
		return bigInteger.toString(16);
	}

	public ResultData analysisdata(String receivecode) {
		// 分析数据
		ResultData device = new ResultData();
		String volthex = receivecode.substring(38, 42);
		device.volt = getvolt(volthex);
		String temphex = receivecode.substring(42, 46);
		device.temperature = gettemperature(temphex);
		String allforlock = receivecode.substring(37, 38);
		char lockcode = allforlock.charAt(0);
		String lockbinary = Hex2Binary(lockcode);
		char elecunlockcode = lockbinary.charAt(3);
		if (elecunlockcode == '0')
			device.elecunlock = false;
		else
			device.elecunlock = true;
		char eleclockcode = lockbinary.charAt(2);
		if (eleclockcode == '0')
			device.eleclock = false;
		else
			device.eleclock = true;
		char handlockcode = lockbinary.charAt(1);
		if (handlockcode == '0')
			device.handlock = false;
		else
			device.handlock = true;
		return device;
	}

	public float gettemperature(String temphex) {
		// 获取温度
		float temp = Integer.parseInt(temphex, 16);
		float finaltemp = (float) (temp * 0.1 - 50);
		return finaltemp;
	}

	public float getvolt(String volthex) {
		// 获取电压
		float volt = Integer.parseInt(volthex, 16);
		float finalvolt = (float) (volt * 0.01);
		return finalvolt;
	}

	public String Hex2Binary(char charAt) {
		// TODO Auto-generated method stub
		switch (charAt) {
		case '0':
			return "0000";
		case '1':
			return "0001";
		case '2':
			return "0010";
		case '3':
			return "0011";
		case '4':
			return "0100";
		case '5':
			return "0101";
		case '6':
			return "0110";
		case '7':
			return "0111";
		case '8':
			return "1000";
		case '9':
			return "1001";
		case 'a':
			return "1010";
		case 'b':
			return "1011";
		case 'c':
			return "1100";
		case 'd':
			return "1101";
		case 'e':
			return "1110";
		case 'f':
			return "1111";
		}
		return null;
	}
}
