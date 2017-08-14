package com.city.model;

public class Management {
	
	private String sensorId;
	private String locking;
	private String generous;
	private String flameDetection;
	private String smokeDetection;
	private String stink;
	private String waterLevel;
	private String waterQuality;
	private String gasDensity;
	private String shockDetection;
	private String operationStatus;
	private String latitude;
	private String longitude;
	private String createDateTime;
	private String installationDateTime;
	private String memo;
	private String cityGeocede;
	private String stateGeocode;
	
	public Management() { 
		
	}
	
	public Management(String sensorId, String locking, String generous, String flameDetection,
		 String smokeDetection, String stink, String waterLevel, String waterQuality
		 , String gasDensity, String shockDetection, String operationStatus, String latitude
		 , String longitude, String createDateTime, String installationDateTime, String memo
		 , String cityGeocede, String stateGeocode) {
		
		this.sensorId = sensorId;
		this.locking = locking;
		this.generous = generous;
		this.flameDetection = flameDetection;
		this.smokeDetection = smokeDetection;
		this.stink = stink;
		this.waterLevel = waterLevel;
		this.waterQuality = waterQuality;
		this.gasDensity = gasDensity;
		this.shockDetection = shockDetection;
		this.operationStatus = operationStatus;
		this.latitude = latitude;
		this.longitude = longitude;
		this.createDateTime = createDateTime;
		this.installationDateTime = installationDateTime;
		this.memo = memo;
		this.cityGeocede = cityGeocede;
		this.stateGeocode = stateGeocode;
	}

	public String getSensorId() {
		return sensorId;
	}

	public String getLocking() {
		return locking;
	}

	public String getGenerous() {
		return generous;
	}

	public String getFlameDetection() {
		return flameDetection;
	}

	public String getSmokeDetection() {
		return smokeDetection;
	}

	public String getStink() {
		return stink;
	}

	public String getWaterLevel() {
		return waterLevel;
	}

	public String getWaterQuality() {
		return waterQuality;
	}

	public String getGasDensity() {
		return gasDensity;
	}

	public String getShockDetection() {
		return shockDetection;
	}

	public String getOperationStatus() {
		return operationStatus;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getCreateDateTime() {
		return createDateTime;
	}

	public String getInstallationDateTime() {
		return installationDateTime;
	}

	public String getMemo() {
		return memo;
	}

	public String getCityGeocede() {
		return cityGeocede;
	}

	public String getStateGeocode() {
		return stateGeocode;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public void setLocking(String locking) {
		this.locking = locking;
	}

	public void setGenerous(String generous) {
		this.generous = generous;
	}

	public void setFlameDetection(String flameDetection) {
		this.flameDetection = flameDetection;
	}

	public void setSmokeDetection(String smokeDetection) {
		this.smokeDetection = smokeDetection;
	}

	public void setStink(String stink) {
		this.stink = stink;
	}

	public void setWaterLevel(String waterLevel) {
		this.waterLevel = waterLevel;
	}

	public void setWaterQuality(String waterQuality) {
		this.waterQuality = waterQuality;
	}

	public void setGasDensity(String gasDensity) {
		this.gasDensity = gasDensity;
	}

	public void setShockDetection(String shockDetection) {
		this.shockDetection = shockDetection;
	}

	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}

	public void setInstallationDateTime(String installationDateTime) {
		this.installationDateTime = installationDateTime;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setCityGeocede(String cityGeocede) {
		this.cityGeocede = cityGeocede;
	}

	public void setStateGeocode(String stateGeocode) {
		this.stateGeocode = stateGeocode;
	}


}
