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
		
	/**
	 * 센서아이디 자동넘버링
	 * @param conn
	 * @param manageType
	 * @return
	 * @throws SQLException
	 */
	public String selectSensorIdNumbering(Connection conn, String manageType) throws SQLException {
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		String manageId = null;
		
		try {
			if(manageType.equals("wm")) {
				pstmt = conn.prepareStatement("select CONCAT('W', LPAD((select(select cast((select right((select max(sensor_id) from sensor_info "
						+ "where sensor_id like 'W%'), 14)) "
						+ "as unsigned) as mInt) + 1 mSum), 14, '0')) sesorId FROM DUAL");
			}
			else if(manageType.equals("tm")) {
				pstmt = conn.prepareStatement("select CONCAT('T', LPAD((select(select cast((select right((select max(sensor_id) from sensor_info "
						+ "where sensor_id like 'T%'), 14)) as unsigned) as mInt) + 1 mSum), 14, '0')) sesorId FROM DUAL");
			}
			else if(manageType.equals("gm")) {
				pstmt = conn.prepareStatement("select CONCAT('G', LPAD((select(select cast((select right((select max(sensor_id) from sensor_info "
						+ "where sensor_id like 'G%'), 14)) as unsigned) as mInt) + 1 mSum), 14, '0')) sesorId FROM DUAL");
			}
			else if(manageType.equals("sm")) {
				pstmt = conn.prepareStatement("select CONCAT('S', LPAD((select(select cast((select right((select max(sensor_id) from sensor_info "
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
	
	/**
	 * 센서 정보 등록
	 * @param conn
	 * @param sensorInfo
	 * @return int
	 * @throws SQLException
	 */
	public int insertSensorInfo(Connection conn, SensorInfo sensorInfo) throws SQLException {
		
		PreparedStatement pstmt = null;
		int resultCode = 0;
		
		try {
			pstmt = conn.prepareStatement("insert into sensor_info"
					+ "(manage_id, sensor_id, sensor_type, operation_status, sensor_notice_standard, sensor_compare, installation_datetime) "
					+ "values (?, ?, ?, ?, ?, ?, now())");
			pstmt.setString(1, sensorInfo.getManageId());
			pstmt.setString(2, sensorInfo.getSensorId());
			pstmt.setString(3, sensorInfo.getSensorType());
			pstmt.setString(4, sensorInfo.getOperationStatus());
			pstmt.setString(5, sensorInfo.getSensorNoticeStandard());
			pstmt.setString(6, sensorInfo.getSensorCompare());	
			resultCode = pstmt.executeUpdate();
			
		} finally {
			JdbcUtil.close(pstmt);
		}
		return resultCode;
	}

	/** 
	 * manageId로 sensorInfoList 를 조회
	 * @param conn
	 * @param manageId
	 * @return
	 * @throws SQLException
	 */
	public List<SensorInfo> selectSensorInfoList(Connection conn, String manageId) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<SensorInfo> sensorInfoList = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement("select * from sensor_info where manage_id =?");
			pstmt.setString(1, manageId);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				sensorInfoList.add(makeSensorFromResultSet(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}		
		return sensorInfoList;
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
	
	/**
	 * 해당 관리 아이디에 대한 센서 타입 조회
	 * @param conn
	 * @param manageId
	 * @return
	 * @throws SQLException
	 */
	public List<String> selectSensorType(Connection conn, String manageId) throws SQLException {
		
		List<String> sensorTypeList = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement("select sensor_type from sensor_info where manage_id = ? ");
			pstmt.setString(1, manageId);
			rs = pstmt.executeQuery();
				
			while (rs.next()) {
				sensorTypeList.add(rs.getString("sensor_type"));
			}
			
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		return sensorTypeList;
	}
}