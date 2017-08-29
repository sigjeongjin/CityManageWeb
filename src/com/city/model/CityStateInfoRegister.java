package com.city.model;

public class CityStateInfoRegister extends Result {
	
	
	public String cityCode;
	public String stateCode;
	
	public CityStateInfoRegister(String cityCode, String stateCode) { 
		this.cityCode = cityCode;
		this.stateCode = stateCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
}
