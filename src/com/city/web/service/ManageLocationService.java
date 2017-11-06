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

	private ManagementDao managementDao = new ManagementDao();

	private Connection conn = null;
	
	public String managementRegister(LocationManagement locationManagement) {
		
		String managementAreaStr = null;

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);
			managementAreaStr = managementDao.insertManagement(conn, locationManagement);
			conn.commit();
			if (managementAreaStr != null) {
				return managementAreaStr;
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

	/** manageId setting
	 * @return
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

	public void managementUpdate(LocationManagement locationManagement) {

		int resultCode = 0;
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			resultCode = managementDao.updateManagementInfo(conn, locationManagement);

			if(resultCode != 1) {
				throw new SQLException("정보 변경에 실패 하였습니다.");
			}
			
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

	public String sensorTypesSelect(String manageId) {
		try (Connection conn = ConnectionProvider.getConnection()) {

			String sensorTypes = managementDao.searchBySensorTypes(conn, manageId);

			if (sensorTypes == null) {
				throw new NullPointerException();
			}

			return sensorTypes;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/** 최근 등록 된 센서등록을 가져오기
	 * @param manageType
	 * @return
	 */
	public List<SensorRegister> getRegisterList(String manageType) {
		
		List<SensorRegister> RegisterList = new ArrayList<>();
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			RegisterList = managementDao.selectRegisterList(conn, manageType);
			conn.commit();			
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return RegisterList;
	}
}
