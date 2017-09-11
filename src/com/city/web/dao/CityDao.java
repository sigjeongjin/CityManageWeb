package com.city.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.model.Address;
import com.city.model.CityAjaxJSON;

import jdbc.JdbcUtil;

public class CityDao {

	public List<Address> selectByCityCode(Connection conn) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement("select * from address_city");
			rs = pstmt.executeQuery();

			List<Address> addressCityList = new ArrayList<>();
			while (rs.next()) {
				Address address = new Address();
				address.setCityCode(rs.getString("city_code"));
				address.setCityName(rs.getString("city_name"));
				addressCityList.add(address);
			}
			return addressCityList;
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}

	}

	public List<Address> selectByStateCode(Connection conn, String citycode) throws SQLException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement("select * from address_state where city_code=?");
			pstmt.setString(1, citycode);
			rs = pstmt.executeQuery();

			List<Address> addressCityList = new ArrayList<>();
			
			while (rs.next()) {
				Address address = new Address();
				address.setStateCode(rs.getString("state_code"));
				address.setStateName(rs.getString("state_name"));
				addressCityList.add(address);
			}
			return addressCityList;
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}
	
	
	public List<CityAjaxJSON> selectCityList(Connection conn) throws SQLException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<CityAjaxJSON> addressCityList = new ArrayList<CityAjaxJSON>();
		
		try {
			pstmt = conn.prepareStatement("select city_code cityCode, city_name cityName from address_city");
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				CityAjaxJSON city = new CityAjaxJSON();
				city.setCityCode(rs.getString("cityCode"));
				city.setCityName(rs.getString("cityName"));
				addressCityList.add(city);
			}
			return addressCityList;
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
	}
}