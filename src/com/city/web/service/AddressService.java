package com.city.web.service;

import com.city.model.Address;
import com.city.model.CityAjaxJSON;
import com.city.web.dao.CityDao;

import jdbc.connection.ConnectionProvider;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressService {

	private CityDao cityDao = new CityDao();

	public List<Address> addressCity() {

		try (Connection conn = ConnectionProvider.getConnection()) {
			List<Address> addressCityList = new ArrayList<>();
			addressCityList = cityDao.selectByCityCode(conn);
			if (addressCityList == null) {
				throw new SQLException();
			}

			return addressCityList;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	public List<Address> addressState(String cityCode) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			List<Address> addressStateList = new ArrayList<>();
			addressStateList = cityDao.selectByStateCode(conn, cityCode);
			if (addressStateList == null) {
				throw new SQLException();
			}

			return addressStateList;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public List<CityAjaxJSON> getCityList() {
		try (Connection conn = ConnectionProvider.getConnection()) {
			List<CityAjaxJSON> addressCityList = new ArrayList<>();
			addressCityList = cityDao.selectCityList(conn);
			if (addressCityList == null) {
				throw new SQLException();
			}

			return addressCityList;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
}
