package org.kzcw.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kzcw.core.BaseServiceImpl;
import org.kzcw.dao.LightboxDao;
import org.kzcw.model.Lightbox;
import org.kzcw.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("lightboxService")
@Component
public class LightboxService extends BaseServiceImpl<Lightbox> {

	private LightboxDao dao;

	@Autowired
	StatusService statusservice;

	@Autowired
	@Qualifier("lightboxDao")
	public void setlightboxDao(LightboxDao dao) {
		this.dao = dao;
	}

	public List<Lightbox> findByAreaName(String area) {
		return dao.findByAreaName(area);
	}
	
	public List<Lightbox> getList(String AREANAME) {
		 return dao.getList(AREANAME);
	}

	public List<Map> getLightboxList(String AREANAME) {
		List<Lightbox> lightboxslist = list();
		List<Map> result = new ArrayList<Map>();
		if (lightboxslist != null) {
			for (Lightbox box : lightboxslist) {
				if (box.getAREANAME().equals(AREANAME)) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("ID", box.getID());
					map.put("NAME", box.getNAME());
					map.put("IEME", box.getIEME());
					map.put("ADDTIME", box.getADDTIME());
					map.put("ISREGIST", box.getISREGIST());
					map.put("USERNAME", box.getAREANAME());
					// 找到最近的一条上报记录
					Status status = statusservice.getRecentRecord(box.getIEME());
					if (status != null) {

						if (status.getDOORSTATUS() == 1) {
							map.put("DOORSTATUS", "关");
						} else {
							map.put("DOORSTATUS", "开");
						}

						if (status.getLOCKSTATUS() == 1) {
							map.put("LOCKSTATUS", "关");
						} else {
							map.put("LOCKSTATUS", "开");
						}
					} else {
						map.put("DOORSTATUS", "未找到上报数据");
						map.put("LOCKSTATUS", "未找到上报数据");
					}
					map.put("ISONLINE", "在线");
					result.add(map);// 添加到结果集中
				}
			}
		}
		return result;
	}
	
	
	public Lightbox findByIMEI(String imei) {
		return dao.findByIMEI(imei);
	}
}
