package com.city.web.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.city.model.LocationManagement;
import com.city.web.dao.ManagementDao;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class ManagementAreaRegisterService {

	private ManagementDao managementDao = new ManagementDao();

	public String ManagementAreaRegister(LocationManagement locationManagement) {
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
}
