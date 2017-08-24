package com.city.web.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.city.model.LocationManagement;
import com.city.model.Member;
import com.city.web.dao.ManagementAreaDao;

import jdbc.connection.ConnectionProvider;

/*
 *  Tm,Wm,Sm,Gm List 각 쓰레기통,수질관리,금연구역관리,도시가스 관리 리스트		 	tl,wl,sl,gl
 * 	Tm,Wm,Sm,Gm Info 각 쓰레기통,수질관리,금연구역관리,도시가스 상세 정보 조회 		 	ti,wi,si,gi
 *  Tm,Wm,Sm,Gm sensorRegister 각 쓰레기통,수질관리,금연구역관리,도시가스 센서정보	tsr,wsr,ssr,gsr
 *  Tm,Wm,Sm,Gm sensorUpdate 각 쓰레기통,수질관리,금연구역관리,도시가스 정보 업데이트	tsu,wsu,ssu,gsu
 *  sensorDelete 센서정보 삭제											sd
 */



public class SensorManageService {

	private ManagementAreaDao managementAreaDao = new ManagementAreaDao();
	private int size = 10;
	
	public SensorListPage getSensorListPage(int pageNum, String manageType) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			int total = managementAreaDao.selectCount(conn, manageType);
			List<LocationManagement> content = managementAreaDao.selectSensorList(conn, (pageNum - 1) * size, size, manageType);
			return new SensorListPage(total, pageNum, size, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
