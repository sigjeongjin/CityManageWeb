package com.city.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.city.model.City;
import com.city.model.SensorInfo;
import com.city.model.SensorResultInfo;

import jdbc.JdbcUtil;

public class ManagementDao {

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
	
	public List<SensorResultInfo>selectSensorListByMemberIdAndManageType(Connection conn, 
			String memberId, String manageType)throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SensorResultInfo> sensorResultInfoList = new ArrayList<SensorResultInfo>();
		try {
			pstmt = conn.prepareStatement("select "
						+" CONCAT((select city_name cityName from address_city where city_code=lm.city_code)"
						+ ",' ',(select state_name stateName from address_state where state_code=lm.state_code)) locationName,"
						+" lm.manage_id manageId "
						+" from location_management lm join sensor_info si on lm.manage_id = si.manage_id "
						+" join member m on lm.state_code = m.state_code "
						+" where lm.manage_type=? and m.member_id=?");
			pstmt.setString(1, manageType);
			pstmt.setString(2, memberId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				SensorResultInfo sensorRsultInfo = new SensorResultInfo();
				sensorRsultInfo.setManageId(rs.getString("manageId"));
				sensorRsultInfo.setLocationName(rs.getString("locationName"));
				sensorResultInfoList.add(sensorRsultInfo);
			}

			return sensorResultInfoList;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	public List<SensorResultInfo>selectSensorListByMemberIdAndManageTypeAndSearchText(Connection conn, 
			String memberId, String manageType, String searchText)throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SensorResultInfo> sensorResultInfoList = new ArrayList<SensorResultInfo>();
		try {
			pstmt = conn.prepareStatement("select "
						+" CONCAT((select city_name cityName from address_city where city_code=lm.city_code)"
						+ ",' ',(select state_name stateName from address_state where state_code=lm.state_code)) locationName,"
						+" lm.manage_id manageId "
						+" from location_management lm join sensor_info si on lm.manage_id = si.manage_id "
						+" join member m on lm.state_code = m.state_code "
						+" where lm.manage_type=? and m.member_id=? and lm.manage_id like ?");
			pstmt.setString(1, manageType);
			pstmt.setString(2, memberId);
			pstmt.setString(3, "%" + searchText + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				SensorResultInfo sensorRsultInfo = new SensorResultInfo();
				sensorRsultInfo.setManageId(rs.getString("manageId"));
				sensorRsultInfo.setLocationName(rs.getString("locationName"));
				sensorResultInfoList.add(sensorRsultInfo);
			}

			return sensorResultInfoList;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
}
