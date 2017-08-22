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

	public List<Address> AddressCity() {

		try (Connection conn = ConnectionProvider.getConnection()) {
			List<Address> addressCityList = new ArrayList<>();
			addressCityList = cityDao.selectByCityGeocodeAndCityName(conn);
			if (addressCityList == null) {
				throw new SQLException();
			}

			return addressCityList;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	public List<Address> AddressState(String cityGeocode) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			List<Address> addressStateList = new ArrayList<>();
			addressStateList = cityDao.selectByStateGeocodeAndCityName(conn, cityGeocode);
			if (addressStateList == null) {
				throw new SQLException();
			}

			return addressStateList;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

}
