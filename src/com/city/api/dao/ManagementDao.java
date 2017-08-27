package com.city.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.JdbcUtil;

public class ManagementDao {

	private String sensorId;

	public int updateBySensorInfo(Connection conn, String sensorId, String senssorInfo)
			throws SQLException {
		PreparedStatement pstmt = null;
		int resultcode = 0;
		
		try {
		
			pstmt = conn.prepareStatement("update sensor_info set sensor_info=? where sensor_id=?");
			pstmt.setString(1, senssorInfo);
			pstmt.setString(2, sensorId);

			resultcode = pstmt.executeUpdate();

			return resultcode;

		} finally {
		
			JdbcUtil.close(pstmt);
		}
	}

	public String updateBySensorIdAndOperationStatus(Connection conn, String sensorId, String operationStatus)
			throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String Resultcode = "200";
		try {
			
			pstmt = conn.prepareStatement("update sensor_info set sensor_info=? where operation_status=?");
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
