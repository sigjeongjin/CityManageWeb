package com.city.model;

public class WmResultInfo extends Result{
	private String manageId;
	private String locationName;
	private String waterQuality;
	private String waterLevel;
	
	public String getManageId() {
		return manageId;
	}
	public void setManageId(String manageId) {
		this.manageId = manageId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getWaterQuality() {
		return waterQuality;
	}
	public void setWaterQuality(String waterQuality) {
		this.waterQuality = waterQuality;
	}
	public String getWaterLevel() {
		return waterLevel;
	}
	public void setWaterLevel(String waterLevel) {
		this.waterLevel = waterLevel;
	} 
}
