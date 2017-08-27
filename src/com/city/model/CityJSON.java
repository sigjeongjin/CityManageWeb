package com.city.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by park on 2017-08-06.
 */

public class CityJSON extends Result {
	List<City> city = new ArrayList<>();

	public List<City> getCity() {
		return city;
	}

	public void setCity(List<City> city) {
		this.city = city;
	}
}
