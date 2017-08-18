package com.city.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.city.model.LocationManagement;

import jdbc.JdbcUtil;

public class ManagementAreaDao {

	public String insertManagementArea(Connection conn, LocationManagement locationManagement) throws SQLException{
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("insert into location_management" + 
					"(manage_id, latitude, longitude, sensor_types, memo, city_geocode, state_geocode,create_datetime) " +
					"values (?, ?, ?, ? ,? ,? ,? , now())");
			pstmt.setString(1, locationManagement.getManageId());
			pstmt.setDouble(2, locationManagement.getLatitude());
			pstmt.setDouble(3, locationManagement.getLongitude());
			pstmt.setString(4, locationManagement.getSensorTypes());
			pstmt.setString(5, locationManagement.getMemo());
			pstmt.setString(6, locationManagement.getCityGeocode());
			pstmt.setString(7, locationManagement.getStateGeocode());	
			//pstmt.setTimestamp(8, new Timestamp(locationManagement.getCreateDatetime().getTime()));
			return Integer.toString(pstmt.executeUpdate());
		} finally {
			JdbcUtil.close(pstmt);
		}
	}
}
