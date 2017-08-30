package com.city.web.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.city.model.LocationManagement;
import com.city.web.dao.ManagementDao;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class ManageLocationService {

	private ManagementDao managementDao = new ManagementDao();

	public String managementRegister(LocationManagement locationManagement) {
		Connection conn = null;
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
			System.out.println("ManagementArea register fail");
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return null;
	}

	public LocationManagement managementInfo(String manageId) {
		try (Connection conn = ConnectionProvider.getConnection()) {

			LocationManagement locationManagement = new LocationManagement();

			locationManagement = managementDao.selectManagementInfo(conn, manageId);

			if (locationManagement == null) {
				System.out.println("select fail");
				throw new NullPointerException();
			}

			return locationManagement;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public String manageIdSet() {
		try (Connection conn = ConnectionProvider.getConnection()) {

			String manageId = managementDao.searchById(conn);

			if (manageId == null) {
				System.out.println("not find manageId");
				throw new NullPointerException();
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
}
