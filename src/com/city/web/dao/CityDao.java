package com.city.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.model.Address;

import jdbc.JdbcUtil;

public class CityDao {

	public List<Address> selectByCityGeocodeAndCityName(Connection conn) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement("select * from address_city");
			rs = pstmt.executeQuery();

			List<Address> addressCityList = new ArrayList<>();
			while (rs.next()) {
				Address address = new Address();
				address.setCityGeocode(rs.getString("city_geocode"));
				address.setCityName(rs.getString("city_name"));
				addressCityList.add(address);
			}
			return addressCityList;
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}

	}

	public List<Address> selectByStateGeocodeAndCityName(Connection conn, String cityGeocode) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement("select * from address_state where city_geocode=?");
			pstmt.setString(1, cityGeocode);
			rs = pstmt.executeQuery();

			List<Address> addressCityList = new ArrayList<>();
			while (rs.next()) {
				Address address = new Address();
				address.setCityGeocode(rs.getString("state_geocode"));
				address.setCityName(rs.getString("state_name"));
				addressCityList.add(address);
			}
			return addressCityList;
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}
}
