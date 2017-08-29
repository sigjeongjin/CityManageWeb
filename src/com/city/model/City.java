package com.city.model;

public class City {

	private String cityCode;
	private String cityName;
	
	public City(String cityName, String cityCode) {
		this.cityCode = cityCode;
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getCityName() {
		return cityName;
	}
}
