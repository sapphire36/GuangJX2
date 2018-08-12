package org.kzcw.common;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.kzcw.common.Iot.HandleResult;
import org.kzcw.common.Iot.youren.ReceiveData;

public class ParsingReceiveQueue {
	//解析队列
	public Queue<ReceiveData> queue = new LinkedList<ReceiveData>();
	public boolean Iswork=true; //是否工作标示
	private AreaEntry usersession;
    //线程池
    ExecutorService executorService = Executors.newFixedThreadPool(10);   
	
    public ParsingReceiveQueue(AreaEntry usersession) {
    	this.usersession=usersession;
    }
    
    
	public void AddMessage(ReceiveData data) {
		//添加消息
		queue.offer(data);
	}
	
	public void StartParsing() {
		Iswork=true;
		while(Iswork) {
			synchronized (queue) {  //拿锁对象
				if(!queue.isEmpty()) {
					 //执行处理
					 executorService.execute(new HandleResult(usersession,queue.poll())); 
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void StopParsing() {
		//停止解析服务
	    Iswork=false;
	    executorService.shutdown(); 
	}
}
