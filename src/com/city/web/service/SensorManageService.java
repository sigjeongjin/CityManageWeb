package com.city.web.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.city.model.GmManagementInfo;
import com.city.model.SensorInfo;
import com.city.model.SmManagementInfo;
import com.city.model.TmManagementInfo;
import com.city.model.WmManagementInfo;
import com.city.web.dao.ManagementDao;
import com.city.web.dao.SensorDao;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class SensorManageService {

	private Connection conn = null;

	private ManagementDao managementDao = new ManagementDao();

	private SensorDao sensorDao = new SensorDao();

	private int size = 10;

	/** Wm List
	 * @param pageNum
	 * @param manageType
	 * @param selectBox
	 * @param searchText
	 * @return
	 */
	public WmSensorListPage getWmSensorListPage(int pageNum, String manageType, String selectBox, String searchText) {

		int total = 0;
		List<WmManagementInfo> content = new ArrayList<>();

		try {
			conn = ConnectionProvider.getConnection();

			total = managementDao.selectCount(conn, manageType, selectBox, searchText);

			content = managementDao.wmSensorList(conn, (pageNum - 1) * size, size, manageType, selectBox, searchText);

		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return new WmSensorListPage(total, pageNum, size, content);
	}
	
	/** Tm List
	 * @param pageNum
	 * @param manageType
	 * @param selectBox
	 * @param searchText
	 * @return
	 */
	public TmSensorListPage getTmSensorListPage(int pageNum, String manageType, String selectBox, String searchText) {

		int total = 0;
		List<TmManagementInfo> content = new ArrayList<>();

		try {
			conn = ConnectionProvider.getConnection();

			total = managementDao.selectCount(conn, manageType, selectBox, searchText);

			content = managementDao.tmSensorList(conn, (pageNum - 1) * size, size, manageType, selectBox, searchText);

		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return new TmSensorListPage(total, pageNum, size, content);
	}

	/** Gm List
	 * @param pageNum
	 * @param manageType
	 * @param selectBox
	 * @param searchText
	 * @return
	 */
	public GmSensorListPage getGmSensorListPage(int pageNum, String manageType, String selectBox, String searchText) {

		int total = 0;
		List<GmManagementInfo> content = new ArrayList<>();

		try {
			conn = ConnectionProvider.getConnection();

			total = managementDao.selectCount(conn, manageType, selectBox, searchText);

			content = managementDao.gmSensorList(conn, (pageNum - 1) * size, size, manageType, selectBox, searchText);

		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return new GmSensorListPage(total, pageNum, size, content);
	}

	/** Sm List
	 * @param pageNum
	 * @param manageType
	 * @param selectBox
	 * @param searchText
	 * @return
	 */
	public SmSensorListPage getSmSensorListPage(int pageNum, String manageType, String selectBox, String searchText) {

		int total = 0;
		List<SmManagementInfo> content = new ArrayList<>();

		try {
			conn = ConnectionProvider.getConnection();

			total = managementDao.selectCount(conn, manageType, selectBox, searchText);

			content = managementDao.smSensorList(conn, (pageNum - 1) * size, size, manageType, selectBox, searchText);

		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return new SmSensorListPage(total, pageNum, size, content);
	}
	
	/**
	 * 센서아이디 자동넘버링
	 * @param manageType
	 * @return
	 */
	public String setSensorId(String manageType) {
		
		String sensorId = null;
		
		try {		
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			sensorId = sensorDao.searchById(conn, manageType);

			conn.commit();
			
			if (StringUtils.isAllEmpty(sensorId)) {
				if (manageType.equals("tm")) {
					sensorId = "T00000000000001";
				} else if (manageType.equals("wm")) {
					sensorId = "W00000000000001";
				} else if (manageType.equals("gm")) {
					sensorId = "G00000000000001";
				} else if (manageType.equals("sm")) {
					sensorId = "S00000000000001";
				}
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		}
		return sensorId;
	}
	
	/** 센서 정보
	 * @param sensorInfo
	 * @return
	 */
	public int setSensorInfo(SensorInfo sensorInfo) {

		int resultCode = 0;

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);
			
			resultCode = sensorDao.insertSensorInfo(conn, sensorInfo);
			
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return resultCode;
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

	/** manageId로 센서 정보 가져오기
	 * @param manageId
	 * @return
	 */
	public List<SensorInfo> getSensorInfo(String manageId) {
		
		List<SensorInfo> sensorInfo = new ArrayList<>();
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			sensorInfo = sensorDao.selectSensorInfo(conn, manageId);
		
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);	
		} 
			return sensorInfo;		
	}
}