package com.city.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.city.model.SensorInfo;

import jdbc.JdbcUtil;

public class SensorDao {
	public String insertSensor(Connection conn, SensorInfo sensorInfo) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("insert into sensor_id"
					+ "(manage_id, sensor_id, sensor_type, operation_status, sensor_notice_standard, installation_datetime) "
					+ "values (?, ?, ?, ? ,? , now())");
			pstmt.setString(1, sensorInfo.getManageId());
			pstmt.setString(2, sensorInfo.getSensorId());
			pstmt.setString(3, sensorInfo.getSensorType());
			pstmt.setString(4, sensorInfo.getOperationStatus());
			pstmt.setString(5, sensorInfo.getSensorNoticeStandard());
			return Integer.toString(pstmt.executeUpdate());
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
}
