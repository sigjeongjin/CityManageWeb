package com.city.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.city.model.LocationManagement;

import jdbc.JdbcUtil;

public class ManagementDao {

	public String insertManagement(Connection conn, LocationManagement locationManagement) throws SQLException {
		PreparedStatement pstmt = null;
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

	public int selectCount(Connection conn, String manageType) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from location_management where manage_type ="  + "'" + manageType + "'");
			
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}

	public List<LocationManagement> selectSensorList(Connection conn, int startRow, int size, String manageType) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		System.out.println("manageType : " + manageType);
		try {
			pstmt = conn.prepareStatement("select * from location_management lm "
					+ "left join address_city city on lm.city_code = city.city_code "
					+ "left join address_state state on lm.state_code= state.state_code "
					+ "where manage_type = ? limit ?, ?");
			pstmt.setString(1, manageType);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, size);
			rs = pstmt.executeQuery();

			List<LocationManagement> sensorList = new ArrayList<>();
			while (rs.next()) {
				sensorList.add(joinSeonsorFromResultSet(rs));
			}
			return sensorList;
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}
	
	
	private LocationManagement joinSeonsorFromResultSet(ResultSet rs) throws SQLException {
		LocationManagement locationManagement = new LocationManagement();
		locationManagement.setManageId(rs.getString("manage_id"));
		locationManagement.setLatitude(rs.getDouble("latitude"));
		locationManagement.setLongitude(rs.getDouble("longitude"));
		locationManagement.setMemo(rs.getString("memo"));
		locationManagement.setCityCode(rs.getString("city_name"));
		locationManagement.setStateCode(rs.getString("state_name"));
		locationManagement.setSensorTypes(rs.getString("sensor_types"));
		locationManagement.setManageType(rs.getString("manage_type"));
		return locationManagement;
	}
	
	private LocationManagement makeSeonsorFromResultSet(ResultSet rs) throws SQLException {
		LocationManagement locationManagement = new LocationManagement();
		locationManagement.setManageId(rs.getString("manage_id"));
		locationManagement.setLatitude(rs.getDouble("latitude"));
		locationManagement.setLongitude(rs.getDouble("longitude"));
		locationManagement.setMemo(rs.getString("memo"));
		locationManagement.setCityCode(rs.getString("city_code"));
		locationManagement.setStateCode(rs.getString("state_code"));
		locationManagement.setSensorTypes(rs.getString("sensor_types"));
		locationManagement.setManageType(rs.getString("manage_type"));
		return locationManagement;
	}

	public LocationManagement selectById(Connection conn, String manageId) throws SQLException  {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from location_management where manage_id=?");
			pstmt.setString(1, manageId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return makeSeonsorFromResultSet(rs);
			} else {
				return null;
			}
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}

	public String searchById(Connection conn) throws SQLException {
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
			manageId1 = manageId1.replaceAll("[^0-9]", "");
			number = Integer.parseInt(manageId1) + 1;
			pstmt2 = conn.prepareStatement("select CONCAT('M', LPAD(" + number + ",6,'0')) FROM DUAL");
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

	public String update(Connection conn, LocationManagement locationManagement) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("update location_management "
					+ "set latitude = ?, longitude = ?, memo = ?, city_code = ?, state_code = ? , sensor_types = ? where manage_id = ?");
			pstmt.setDouble(1, locationManagement.getLatitude());
			pstmt.setDouble(2, locationManagement.getLongitude());
			pstmt.setString(3, locationManagement.getMemo());
			pstmt.setString(4, locationManagement.getCityCode());
			pstmt.setString(5, locationManagement.getStateCode());
			pstmt.setString(6, locationManagement.getSensorTypes());
			pstmt.setString(7, locationManagement.getManageId());
			return Integer.toString(pstmt.executeUpdate());
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
// **************************************************

}
