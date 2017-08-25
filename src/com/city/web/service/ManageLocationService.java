package com.city.web.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.city.model.LocationManagement;
import com.city.model.Member;
import com.city.web.dao.ManagementAreaDao;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class ManageLocationService {

	private ManagementAreaDao managementAreaDao = new ManagementAreaDao();

	public String managementAreaRegister(LocationManagement locationManagement) {
		Connection conn = null;
		String managementAreaStr = null;

		try {
			conn = ConnectionProvider.getConnection(); // transaction
			conn.setAutoCommit(false);
			managementAreaStr = managementAreaDao.insertManagementArea(conn, locationManagement);
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

	public LocationManagement manageLocationSelect(String manageId) {
		try (Connection conn = ConnectionProvider.getConnection()) {

			LocationManagement locationManagement = new LocationManagement();

			locationManagement = managementAreaDao.selectById(conn, manageId);

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
}
