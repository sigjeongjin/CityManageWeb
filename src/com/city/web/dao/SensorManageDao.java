package com.city.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.city.model.GmSensor;
import com.city.model.SensorInfo;

import jdbc.JdbcUtil;

public class SensorManageDao {

	public String insertSensorRegiste(Connection conn, SensorInfo sensor) throws SQLException{
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("insert into sensor_info" + 
					"(sensor_id, latitude, longitude, create_datetime, installation_datetime, memo, city_geocode, state_geocode) " +
					"values (?, ?, ?, ? ,? ,? ,? ,?)");
			pstmt.setString(1, sensor.getSensorId());
			pstmt.setDouble(2, sensor.getLatitude());
			pstmt.setDouble(3, sensor.getLongitude());
			pstmt.setTimestamp(4, new Timestamp(sensor.getCreateDatetime().getTime()));
			pstmt.setTimestamp(5, new Timestamp(sensor.getInstallationDatetime().getTime()));
			pstmt.setString(6, sensor.getMemo());
			pstmt.setString(7, sensor.getCityGeocode());
			pstmt.setString(8, sensor.getStateGeocode());	
			return Integer.toString(pstmt.executeUpdate());
		} finally {
			JdbcUtil.close(conn);
			JdbcUtil.close(pstmt);
		}
	}

	public String insertGmSensorRegiste(Connection conn, GmSensor gmSensor) throws SQLException {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement("insert into gm_sensor_info" + 
					"(sensor_id, gas_density, shock_detection, operation_status, sensor_notice_standard) "+
					"values (?, ?, ?, ?, ?)");
			pstmt.setString(1, gmSensor.getSensorId());
			pstmt.setString(2, gmSensor.getGasDensity());
			pstmt.setString(3, gmSensor.getShockDetection());
			pstmt.setString(4, gmSensor.getOperationStatus());
			pstmt.setInt(5, gmSensor.getSensorNoticeStandard());
		} finally {
			JdbcUtil.close(conn);
			JdbcUtil.close(pstmt);
		}
		return null;
	}
}
