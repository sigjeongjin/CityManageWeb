package com.city.model;

import java.util.Date;

public class LocationManagement {

	private String manageId;
	private double latitude;
	private double longitude;
	private Date createDatetime;
	private String memo;
	private String cityGeocode;
	private String stateGeocode;
	private String sensorTypes;
	private String manageTypes;

	public String getManageId() {
		return manageId;
	}

	public void setManageId(String manageId) {
		this.manageId = manageId;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCityGeocode() {
		return cityGeocode;
	}

	public void setCityGeocode(String cityGeocode) {
		this.cityGeocode = cityGeocode;
	}

	public String getStateGeocode() {
		return stateGeocode;
	}

	public void setStateGeocode(String stateGeocode) {
		this.stateGeocode = stateGeocode;
	}

	public String getSensorTypes() {
		return sensorTypes;
	}

	public void setSensorTypes(String sensorTypes) {
		this.sensorTypes = sensorTypes;
	}

	public String getManageTypes() {
		return manageTypes;
	}

	public void setManageTypes(String manageTypes) {
		this.manageTypes = manageTypes;
	}
}