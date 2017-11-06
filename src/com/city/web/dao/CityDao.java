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

	/**  
	 * cityCode로 cityName 조회
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<Address> selectCityCode(Connection conn) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Address> addressCityList = new ArrayList<>();

		try {
			pstmt = conn.prepareStatement("SELECT city_code, city_name FROM address_city");
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Address address = new Address();
				address.setCityCode(rs.getString("city_code"));
				address.setCityName(rs.getString("city_name"));
				addressCityList.add(address);
			}
			
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		return addressCityList;
	}
	
	/** 
	 * stateCode로 stateName 조회
	 * @param conn
	 * @param cityCode
	 * @return
	 * @throws SQLException
	 */
	public List<Address> selectStateCode(Connection conn, String cityCode) throws SQLException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Address> addressCityList = new ArrayList<>();

		try {
			pstmt = conn.prepareStatement("SELECT state_code, state_name FROM address_state WHERE city_code=?");
			pstmt.setString(1, cityCode);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Address address = new Address();
				address.setStateCode(rs.getString("state_code"));
				address.setStateName(rs.getString("state_name"));
				addressCityList.add(address);
			}		
			
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		return addressCityList;
	}
	
	/**
	 * @param conn : 커넥션
	 * @return List<CityAjaxJSON>
	 * @throws SQLException
	 */
	public List<CityAjaxJSON> selectCityList(Connection conn) throws SQLException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<CityAjaxJSON> addressCityList = new ArrayList<CityAjaxJSON>();
		
		try {
			pstmt = conn.prepareStatement("SELECT city_code cityCode, city_name cityName FROM address_city");
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				CityAjaxJSON city = new CityAjaxJSON();
				city.setCityCode(rs.getString("cityCode"));
				city.setCityName(rs.getString("cityName"));
				addressCityList.add(city);
			}	
			
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(rs);
		}
		return addressCityList;
	}
}