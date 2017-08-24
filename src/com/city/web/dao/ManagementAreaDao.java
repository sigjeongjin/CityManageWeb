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
			pstmt = conn.prepareStatement("select * from location_management "
					+ "where manage_type = ? limit ?, ?");
			pstmt.setString(1, manageType);		
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, size);
			rs = pstmt.executeQuery();

			List<LocationManagement> sensorList = new ArrayList<>();
			while (rs.next()) {
				sensorList.add(makeSeonsorFromResultSet(rs));
			}
			return sensorList;
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}
	

//	
//	public int selectCount(Connection conn, String manageType) throws SQLException {
//		Statement stmt = null;
//		ResultSet rs = null;
//		try {
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery("select count(*) from location_management");
//			if (rs.next()) {
//				return rs.getInt(1);
//			}
//			return 0;
//		} finally {
//			JdbcUtil.close(rs);
//			JdbcUtil.close(stmt);
//		}
//	}
//
//	public List<LocationManagement> selectSensorList(Connection conn, int startRow, int size, String manageType) throws SQLException {
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		System.out.println("manageType : " + manageType);
//		try {
//			pstmt = conn.prepareStatement("select * from location_management limit ?, ?");
//			//pstmt.setString(1, manageType);		
//			pstmt.setInt(1, startRow);
//			pstmt.setInt(2, size);
//			rs = pstmt.executeQuery();
//
//			List<LocationManagement> sensorList = new ArrayList<>();
//			while (rs.next()) {
//				sensorList.add(makeSeonsorFromResultSet(rs));
//			}
//			return sensorList;
//		} finally {
//			JdbcUtil.close(pstmt);
//			JdbcUtil.close(rs);
//		}
//	}
	private LocationManagement makeSeonsorFromResultSet(ResultSet rs) throws SQLException {
//		System.out.println("manage_id : " + rs.getString("manage_id"));
//		System.out.println("latitude : " + rs.getString("latitude"));
//		System.out.println("longitude : " + rs.getString("longitude"));
//		System.out.println("memo : " + rs.getString("memo"));
//		System.out.println("city_code : " + rs.getString("city_code"));
//		System.out.println("state_code : " + rs.getString("state_code"));
//		System.out.println("sensor_types : " + rs.getString("sensor_types"));
//		System.out.println("manage_type : " + rs.getString("manage_type"));
		
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
}
