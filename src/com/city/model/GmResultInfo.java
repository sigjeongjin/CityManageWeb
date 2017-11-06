package com.city.model;

/**
 * @author com 센서아이디,주소,압력,충격,설치날짜
 */
public class GmResultInfo extends Result {
	
	public GmResultInfo() {
		super();
	}
	
	public GmResultInfo(String manageId, String locationName, String gasDensity, String shockDetection,
			String installationDateTime, String bookmark) {
		super();
		this.manageId = manageId;
		this.locationName = locationName;
		this.gasDensity = gasDensity;
		this.shockDetection = shockDetection;
		this.installationDateTime = installationDateTime;
		this.bookmark = bookmark;
	}

	private String manageId;
	private String locationName;
	private String gasDensity;
	private String shockDetection;
	private String installationDateTime;
	private String bookmark;

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

	public String getGasDensity() {
		return gasDensity;
	}

	public void setGasDensity(String gasDensity) {
		this.gasDensity = gasDensity;
	}

	public String getShockDetection() {
		return shockDetection;
	}

	public void setShockDetection(String shockDetection) {
		this.shockDetection = shockDetection;
	}

	public String getInstallationDateTime() {
		return installationDateTime;
	}

	public void setInstallationDateTime(String installationDateTime) {
		this.installationDateTime = installationDateTime;
	}

	public String getBookmark() {
		return bookmark;
	}

	public void setBookmark(String bookmark) {
		this.bookmark = bookmark;
	}
}
