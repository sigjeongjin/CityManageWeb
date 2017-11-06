package com.city.model;

/**
 * @author com 센서아이디,주소,불꽃,연기,설치날짜
 */
public class SmResultInfo extends Result {
	private String manageId;
	private String locationName;
	private String flameDetection;
	private String smokeDetection;
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

	public String getFlameDetection() {
		return flameDetection;
	}

	public void setFlameDetection(String flameDetection) {
		this.flameDetection = flameDetection;
	}

	public String getSmokeDetection() {
		return smokeDetection;
	}

	public void setSmokeDetection(String smokeDetection) {
		this.smokeDetection = smokeDetection;
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
