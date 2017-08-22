package com.city.model;

public class Address {
	
	private String cityGeocode;
	private String cityName;
	private String stateGeocode;
	private String stateName;

	public String getCityGeocode() {
		return cityGeocode;
	}

	public void setCityGeocode(String cityGeocode) {
		this.cityGeocode = cityGeocode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStateGeocode() {
		return stateGeocode;
	}

	public void setStateGeocode(String stateGeocode) {
		this.stateGeocode = stateGeocode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
