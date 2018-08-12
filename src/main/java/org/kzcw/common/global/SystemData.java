package org.kzcw.common.global;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.kzcw.common.AreaEntry;

public class SystemData {
	//系统数据
	private static SystemData instance=new SystemData();
	private Map<String,AreaEntry> arealist = new HashMap<String,AreaEntry>();
	
	public SystemData() {
	}
	
	public static SystemData getInstance() {
		return instance;
	}
	
	public void addAreaEntry(AreaEntry areaentry) {
		//添加项目
		arealist.put(String.valueOf(areaentry.getArea().getAREANAME()),areaentry);
	}
	
	public void delAreaEntry(String areaEntry) {
		//删除项目
		AreaEntry temp = getAreaEntry(areaEntry);
		if(temp!=null) {
	        new Thread("Stop work"){
	            @Override
	            public void run(){
	    			try {
	    				temp.getYourenmanager().doStop();
	    			} catch (MqttException e) {
	    				e.printStackTrace();
	    			}
	            }
	        }.start();
			arealist.remove(areaEntry);
		}
	}
	
	public AreaEntry getAreaEntry(String areaname) {
		return arealist.get(areaname);
	}
	
	public List<AreaEntry> getAreaEntryList(){
		//返回MapSet
		List<AreaEntry> resultlist=new ArrayList<AreaEntry>();
        for(Map.Entry<String, AreaEntry> entry: arealist.entrySet())
        {
    		 resultlist.add(entry.getValue());
        }
		return resultlist;
	}
}
