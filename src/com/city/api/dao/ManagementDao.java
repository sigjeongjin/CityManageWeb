package com.city.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.JdbcUtil;

public class ManagementDao {

	private String sensorId;

	public int sensorRegister(Connection conn, String sensorId , String sensorInfo, String sensorType)
			throws SQLException {
		PreparedStatement pstmt = null;
		int resultcode = 0;
		

		try {
		
			pstmt = conn.prepareStatement("insert into sensor_info "
					+ "(sensori_id, sensor_info, sensor_type) "
					+ "values (?, ?, ?)");
			pstmt.setString(1, sensorId);
			pstmt.setString(2, sensorInfo);
			pstmt.setString(3, sensorType);

			

			pstmt.executeUpdate();
			return resultcode;

		} finally {

			JdbcUtil.close(pstmt);
		}

	}

	public String insertOperationStatus(Connection conn, String sensorId, String operationStatus)
			throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String Resultcode = "200";
		try {
			
			pstmt = conn.prepareStatement("insert into location_management and sensor_info"
					+ "sensor_info=? where operation_status=?");
			pstmt.setString(1, sensorId);
			pstmt.setString(2, operationStatus);

			return Integer.toString(pstmt.executeUpdate());

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}
	}

}
