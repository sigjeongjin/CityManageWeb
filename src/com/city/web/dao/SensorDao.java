package com.city.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import com.city.model.SensorInfo;
import jdbc.JdbcUtil;

public class SensorDao {
	
	// sensor 등록 insert문
	public String insertSensor(Connection conn, SensorInfo sensorInfo) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("insert into sensor_info"
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

	public String searchById(Connection conn, String manageType) throws SQLException {
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		int number = 0;
		String sensorId1 = null;
		String sensorId2 = null;

		try {
			pstmt1 = conn.prepareStatement("select max(sensor_id) from sensor_info");
			rs1 = pstmt1.executeQuery();
			while (rs1.next()) {
				sensorId1 = rs1.getString(1);
			}
			sensorId1 = sensorId1.replaceAll("[^0-9]", "");
			number = Integer.parseInt(sensorId1) + 1;
			pstmt2 = conn.prepareStatement("select CONCAT('W', LPAD(" + number + ",5,'0')) FROM DUAL");
			rs2 = pstmt2.executeQuery();
			while (rs2.next()) {
				sensorId2 = rs2.getString(1);
			}

			System.out.println("sensorId2 : " + sensorId2);
		} finally {
			JdbcUtil.close(pstmt1);
			JdbcUtil.close(rs1);
			JdbcUtil.close(pstmt2);
			JdbcUtil.close(rs2);
		}
		return sensorId2;
	}

	public List<SensorInfo> searchByType(Connection conn, String sensorManageId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select sensor_type from sensor_info where manage_id =?");
			pstmt.setString(1, sensorManageId);
			rs = pstmt.executeQuery();
			List<SensorInfo> sensorInfoList = new ArrayList<>();
			while (rs.next()) {

				sensorInfoList.add(makeSensorTypeFromResultSet(rs));
			}
			return sensorInfoList;

		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}

	private SensorInfo makeSensorTypeFromResultSet(ResultSet rs) throws SQLException {
		SensorInfo sensorInfo = new SensorInfo();
		sensorInfo.setManageId(rs.getString("manage_id"));
		sensorInfo.setSensorId(rs.getString("sensor_id"));
		sensorInfo.setSensorType(rs.getString("sensor_type"));
		sensorInfo.setOperationStatus(rs.getString("operation_status"));
		sensorInfo.setSensorNoticeStandard(rs.getString("sensor_notice_standard"));
		return sensorInfo;
	}

	// 해당 manageId sensorInfo 정보를 불러오기 위한 select문
	public List<SensorInfo> selectByManageId(Connection conn, String manageId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from sensor_info where manage_id =?");
			pstmt.setString(1, manageId);
			rs = pstmt.executeQuery();
			List<SensorInfo> sensorInfoList = new ArrayList<>();
			while (rs.next()) {

				sensorInfoList.add(makeSensorTypeFromResultSet(rs));
			}
			return sensorInfoList;

		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}
}
