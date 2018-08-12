package org.kzcw.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.kzcw.common.Iot.utils.OperateMessage;

public class OperateList {
	// 开箱队列
	public List<OperateMessage> list = new LinkedList<OperateMessage>();
	public boolean IsFlush = false;

	public void addOperateItem(OperateMessage message) {
		// 入队,有人申请开锁时添加该队列
		IsFlush = true; // 执行更新
		list.add(message);
	}

	public OperateMessage delOperateItem(int index) {
		// 出队,管理人员打开锁时,将出队队头元素
		if (list.size() <= 0) {
			return null;
		}
		if (index > list.size() || index < 0) {
			return null;
		} else {
			IsFlush = true; // 执行刷新
			return list.remove(index);
		}
	}

	public List<OperateMessage> getOperateMessageList(boolean isOpen) {
		List<OperateMessage> result = new ArrayList<OperateMessage>();
		for (OperateMessage message : list) {
			if (message.isOpen == isOpen) {
				result.add(message);
			}
		}
		return result;
	}
	
	public List<OperateMessage> getOperateMessageList() {
		return list;
	}

	public OperateMessage delItemByEMEI(String emei) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).EMEI.equals(emei)) {
				IsFlush = true; // 执行刷新
				return list.remove(i);
			}
		}
		return null;
	}
	
	public OperateMessage getItemByEMEI(String emei) {
		for (OperateMessage message:list) {
			if (message.EMEI.equals(emei)) {
				 return message;
			}
		}
		return null;
	}

	public boolean isEmpty() {
		// 判断队列是否为空
		return list.isEmpty();
	}
}
