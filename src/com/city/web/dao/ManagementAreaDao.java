package com.city.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.city.model.LocationManagement;

import jdbc.JdbcUtil;

public class ManagementAreaDao {

	public String insertManagementArea(Connection conn, LocationManagement locationManagement) throws SQLException {
		PreparedStatement pstmt = null;
		String test = manageIdNumber(conn);
		System.out.println("manageIdNumber : " + test);
		try {
			pstmt = conn.prepareStatement("insert into location_management"
					+ "(manage_id, latitude, longitude, manage_type, sensor_types, memo, city_code, state_code, create_datetime) "
					+ "values (?, ?, ?, ? ,? ,?, ?, ?, now())");
			pstmt.setString(1, locationManagement.getManageId());
			pstmt.setDouble(2, locationManagement.getLatitude());
			pstmt.setDouble(3, locationManagement.getLongitude());
			pstmt.setString(4, locationManagement.getManageType());
			pstmt.setString(5, locationManagement.getSensorTypes());
			pstmt.setString(6, locationManagement.getMemo());
			pstmt.setString(7, locationManagement.getCityCode());
			pstmt.setString(8, locationManagement.getStateCode());
			return Integer.toString(pstmt.executeUpdate());
		} finally {
			JdbcUtil.close(pstmt);
		}
	}

	public String manageIdNumber(Connection conn) throws SQLException {
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		int number = 0;
		String manageId1 = null;
		String manageId2 = null;

		try {
			pstmt1 = conn.prepareStatement("select max(manage_id) from location_management");
			rs1 = pstmt1.executeQuery();
			while (rs1.next()) {
				manageId1 = rs1.getString(1);
			}
			manageId1 = manageId1.replaceAll("[^1-9]", "");
			number = Integer.parseInt(manageId1) + 1;
			pstmt2 = conn.prepareStatement("select CONCAT('M', LPAD(" + number + ",5,'0')) FROM DUAL");
			rs2 = pstmt2.executeQuery();
			while (rs2.next()) {
				manageId2 = rs2.getString(1);
			}

			System.out.println("manageId2 : " + manageId2);
		} finally {
			JdbcUtil.close(pstmt1);
			JdbcUtil.close(rs1);
			JdbcUtil.close(pstmt2);
			JdbcUtil.close(rs2);
		}
		return manageId2;
	}
}
