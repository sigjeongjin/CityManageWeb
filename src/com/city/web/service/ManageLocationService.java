package com.city.web.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public String manageIdSet() {
		try (Connection conn = ConnectionProvider.getConnection()) {

			String manageId = managementDao.searchById(conn);

			if (manageId == null) {
				manageId = "M00000000000001";
			}

			return manageId;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public String managementUpdate(LocationManagement locationManagement) {
		Connection conn = null;
		String mU = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			String strId = managementDao.updateManagementInfo(conn, locationManagement);
			conn.commit();
			if (strId != null) {
				mU = "Y";
				return mU;
			} else {
				mU = "N";
				throw new SQLException();
			}
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

	public List<SensorRegister> RegisterList(String manageType) {
		List<SensorRegister> tmRegisterList = new ArrayList<>();
		try (Connection conn = ConnectionProvider.getConnection()) {
			
			tmRegisterList = managementDao.selectTmRegisterList(conn, manageType);
			
			return tmRegisterList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
