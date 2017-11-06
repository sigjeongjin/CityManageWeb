package com.city.web.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.city.model.Address;
import com.city.model.CityAjaxJSON;
import com.city.web.dao.CityDao;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class AddressService {

	private CityDao cityDao = new CityDao();

	private Connection conn = null;

	public List<Address> addressCity() {

		List<Address> addressCityList = new ArrayList<>();

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			addressCityList = cityDao.selectByCityCode(conn);

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return addressCityList;
	}

	/** state의 주소값 가져오기
	 * @param cityCode
	 * @return
	 */
	public List<Address> getAddressState(String cityCode) {

		List<Address> addressStateList = new ArrayList<>();
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			addressStateList = cityDao.selectStateCode(conn, cityCode);

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return addressStateList;
	}

	public List<CityAjaxJSON> getCityList() {

		List<CityAjaxJSON> addressCityList = new ArrayList<>();
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			addressCityList = cityDao.selectCityList(conn);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
		} finally {
			JdbcUtil.close(conn);
		}
		return addressCityList;
	}
}
