package com.city.web.service;

import com.city.model.Address;
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

	public List<Address> addressState(String cityGeocode) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			List<Address> addressStateList = new ArrayList<>();
			addressStateList = cityDao.selectByStateCode(conn, cityGeocode);
			if (addressStateList == null) {
				throw new SQLException();
			}

			return addressStateList;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

}
