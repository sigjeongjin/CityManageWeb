package com.city.web.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.model.GmManagementInfo;
import com.city.model.SensorInfo;
import com.city.model.SmManagementInfo;
import com.city.model.TmManagementInfo;
import com.city.model.WmManagementInfo;
import com.city.web.dao.ManagementDao;
import com.city.web.dao.SensorDao;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

/*
 *  Tm,Wm,Sm,Gm List 각 쓰레기통,수질관리,금연구역관리,도시가스 관리 리스트		 	tl,wl,sl,gl
 * 	Tm,Wm,Sm,Gm Info 각 쓰레기통,수질관리,금연구역관리,도시가스 상세 정보 조회 		ti,wi,si,gi
 *  Tm,Wm,Sm,Gm sensorRegister 각 쓰레기통,수질관리,금연구역관리,도시가스 센서정보	tsr,wsr,ssr,gsr
 *  Tm,Wm,Sm,Gm sensorUpdate 각 쓰레기통,수질관리,금연구역관리,도시가스 정보 업데이트	tsu,wsu,ssu,gsu
 *  sensorDelete 센서정보 삭제												sd
 */

public class SensorManageService {

	private ManagementDao managementDao = new ManagementDao();
	private SensorDao sensorDao = new SensorDao();
	private int size = 10;
	
	// manageType: tm, wm, gm, sm
	// manageType: tm
	public TmSensorListPage getTmSensorListPage(int pageNum, String manageType, String selectBox, String searchText) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			int total = managementDao.selectCount(conn, manageType, selectBox, searchText);
			List<TmManagementInfo> content = managementDao.tmSensorList(conn, (pageNum - 1) * size, size, manageType, selectBox, searchText);
			return new TmSensorListPage(total, pageNum, size, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// manageType: wm
	public WmSensorListPage getWmSensorListPage(int pageNum, String manageType, String selectBox, String searchText) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			int total = managementDao.selectCount(conn, manageType, selectBox, searchText);
			List<WmManagementInfo> content = managementDao.wmSensorList(conn, (pageNum - 1) * size, size, manageType, selectBox, searchText);
			return new WmSensorListPage(total, pageNum, size, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	// manageType: gm
	public GmSensorListPage getGmSensorListPage(int pageNum, String manageType, String selectBox, String searchText) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			int total = managementDao.selectCount(conn, manageType, selectBox, searchText);
			List<GmManagementInfo> content = managementDao.gmSensorList(conn, (pageNum - 1) * size, size, manageType, selectBox, searchText);
			return new GmSensorListPage(total, pageNum, size, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	// manageType: sm
	public SmSensorListPage getSmSensorListPage(int pageNum, String manageType, String selectBox, String searchText) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			int total = managementDao.selectCount(conn, manageType, selectBox, searchText);
			List<SmManagementInfo> content = managementDao.smSensorList(conn, (pageNum - 1) * size, size, manageType, selectBox, searchText);
			return new SmSensorListPage(total, pageNum, size, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
		
	public String sensorRegister(SensorInfo sensorInfo) {
		Connection conn = null;
		String sensorRegister = null;

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);
			sensorRegister = sensorDao.insertSensor(conn, sensorInfo);
			conn.commit();
			if (sensorRegister != null) {
				return sensorRegister;
			} else {
				throw new SQLException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return null;
	}
	
	public String sensorIdSet(String manageType) {
		try (Connection conn = ConnectionProvider.getConnection()) {

			String sensorId = sensorDao.searchById(conn, manageType);

			if (sensorId == null) {
				if (manageType.equals("tm")) {
					sensorId = "T00000000000001";
				} else if(manageType.equals("wm")) {
					sensorId = "W00000000000001";
				} else if(manageType.equals("gm")) {
					sensorId = "G00000000000001";
				} else if(manageType.equals("sm")) {
					sensorId = "S00000000000001";
				}
			}

			return sensorId;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<String> sensorTypeSelect(String manageId) {
		try (Connection conn = ConnectionProvider.getConnection()) {

			List<String> sensorTypeList = new ArrayList<>();
			sensorTypeList = sensorDao.searchByType(conn, manageId);

			if (sensorTypeList == null) {
				throw new NullPointerException();
			}

			return sensorTypeList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<SensorInfo> selectSensor(String manageId) {
		try (Connection conn = ConnectionProvider.getConnection()) {

			List<SensorInfo> sensorInfo = new ArrayList<>();
			sensorInfo = sensorDao.selectByManageId(conn, manageId);

			if (sensorInfo == null) {
				throw new NullPointerException();
			}

			return sensorInfo;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}



