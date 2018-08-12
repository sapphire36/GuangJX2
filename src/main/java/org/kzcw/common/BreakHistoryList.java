package org.kzcw.common;
import java.util.ArrayList;
import java.util.List;
import org.kzcw.model.Breakhistory;

public class BreakHistoryList {
    //报警信息表
	//hashmap 提高查询速度
	private List<Breakhistory> list = new ArrayList<Breakhistory>();
	private AreaEntry areaEntry;
	private String current;//当前报警
	
	public BreakHistoryList(AreaEntry areaentry) {
		this.areaEntry=areaentry;
	}
	
	public void addItem(Breakhistory content) {
		//添加项目
		content.setAREANAME(areaEntry.getArea().getAREANAME());
		list.add(content);
	}
	
	public Breakhistory getItem(String emei) {
		for(Breakhistory breakhistory:list) {
			if(breakhistory.getIEME().equals(emei)) {
				return breakhistory;
			}
		}
		return null;
	}
	
	public List<Breakhistory> getBreakHistoryList(){
		//返回List
		return list;
	}
}
