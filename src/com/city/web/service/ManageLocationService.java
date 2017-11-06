package com.city.web.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.city.model.LocationManagement;
import com.city.model.SensorRegister;
import com.city.web.dao.ManagementDao;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class ManageLocationService {

	private Connection conn = null;
	
	private ManagementDao managementDao = new ManagementDao();

	/** 
	 * 관리아이디 자동넘버링
	 * @return String
	 */
	public String setManageId() {
		String manageId = "";
		
		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);
			
			manageId = managementDao.selectManageId(conn);

			// 처음 등록 시 등록 된 아이디가 없을 경우
			if (StringUtils.isAllEmpty(manageId)) {
				manageId = "M00000000000001";
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return manageId;
	}
	
	/** 
	 * 관리 정보
	 * @param locationManagement : 관리 정보
	 * @return int
	 */
	public int setManagementInfo(LocationManagement locationManagement) {
		
		int resultCode = 0;

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);
			
			resultCode = managementDao.insertManagementInfo(conn, locationManagement);
			
			conn.commit();
	
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return resultCode;
	}
	
	/**
	 * 관리 수정 정보
	 * @param locationManagement  : 관리 정보
	 * @return int
	 */
	public int modifiyManagement(LocationManagement locationManagement) {

		int resultCode = 0;
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			resultCode = managementDao.updateManagement(conn, locationManagement);
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return resultCode;
	}
	
	/**
	 * 센서타입들 가져오기
	 * @param manageId : 관리아이디
	 * @return String
	 */
	public String getSensorTypes(String manageId) {
		
		String sensorTypes = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			sensorTypes = managementDao.selectSensorTypes(conn, manageId);
	
			conn.commit();		
			
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}	
		return sensorTypes;
	}

	public LocationManagement managementInfo(String manageId) {

		LocationManagement locationManagement = new LocationManagement();
		
		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);
			
			locationManagement = managementDao.selectManagementInfo(conn, manageId);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return locationManagement;
	}



	/** 최근 등록 된 센서등록을 가져오기
	 * @param manageType
	 * @return
	 */
	public List<SensorRegister> getRegisterList(String manageType) {
		
		List<SensorRegister> sensorRegister = new ArrayList<>();
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			sensorRegister = managementDao.selectRegisterList(conn, manageType);
			conn.commit();			
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return sensorRegister;
	}
}
