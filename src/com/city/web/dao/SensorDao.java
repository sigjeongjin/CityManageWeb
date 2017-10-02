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


	/** sensorId NUMBER AUTO SELECT문 */
	public String searchById(Connection conn, String manageType) throws SQLException {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String manageId = null;
		
		try {
			if(manageType.equals("tm")) {
				pstmt = conn.prepareStatement("select CONCAT('T', LPAD((select(select cast((select right((select max(sensor_id) from sensor_info "
						+ "where sensor_id like 'T%'), 14)) "
						+ "as unsigned) as mInt) + 1 mSum), 14, '0')) sesorId FROM DUAL");
			}
			else if(manageType.equals("wm")) {
				pstmt = conn.prepareStatement("select CONCAT('W', LPAD((select(select cast((select right((select max(sensor_id) from sensor_info "
						+ "where sensor_id like 'W%'), 14)) as unsigned) as mInt) + 1 mSum), 14, '0')) sesorId FROM DUAL");
			}
			else if(manageType.equals("gm")) {
				pstmt = conn.prepareStatement("select CONCAT('T', LPAD((select(select cast((select right((select max(sensor_id) from sensor_info "
						+ "where sensor_id like 'G%'), 14)) as unsigned) as mInt) + 1 mSum), 14, '0')) sesorId FROM DUAL");
			}
			else if(manageType.equals("sm")) {
				pstmt = conn.prepareStatement("select CONCAT('T', LPAD((select(select cast((select right((select max(sensor_id) from sensor_info "
						+ "where sensor_id like 'S%'), 14)) as unsigned) as mInt) + 1 mSum), 14, '0')) sesorId FROM DUAL");
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				manageId = rs.getString(1);
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return manageId;
	}

/*	public List<SensorInfo> searchByType(Connection conn, String sensorManageId) throws SQLException {
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
	}*/
	
/*	private SensorInfo makeSensorTypeFromResultSet(ResultSet rs) throws SQLException {
		SensorInfo sensorInfo = new SensorInfo();
		sensorInfo.setSensorType(rs.getString("sensor_type"));
		return sensorInfo;
	}*/

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

				sensorInfoList.add(makeSensorFromResultSet(rs));
			}
			return sensorInfoList;

		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}
	
	// 해당 manageId sensorInfo 정보를 불러오기 위한 select문
	public List<String> searchByType(Connection conn, String manageId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select sensor_type from sensor_info where manage_id = ? ");
			pstmt.setString(1, manageId);
			rs = pstmt.executeQuery();
			List<String> sensorTypeList = new ArrayList<>();
			while (rs.next()) {

				sensorTypeList.add(rs.getString("sensor_type"));
			}
			return sensorTypeList;

		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}
	
	private SensorInfo makeSensorFromResultSet(ResultSet rs) throws SQLException {
		SensorInfo sensorInfo = new SensorInfo();
		sensorInfo.setManageId(rs.getString("manage_id"));
		sensorInfo.setSensorId(rs.getString("sensor_id"));
		sensorInfo.setSensorType(rs.getString("sensor_type"));
		sensorInfo.setOperationStatus(rs.getString("operation_status"));
		sensorInfo.setSensorNoticeStandard(rs.getString("sensor_notice_standard"));
		return sensorInfo;
	}
}
