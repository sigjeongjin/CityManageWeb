package com.city.model;

/**
 * @author com 센서아이디,주소,불꽃,악취,쓰레기량,잠금,설치날짜
 */
public class TmResultInfo extends Result {
	private String manageId;
	private String locationName;
	private String flameDetection;
	private String stink;
	private String generous;
	private String lock;
	private String installationDateTime;

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

	public String getStink() {
		return stink;
	}

	public void setStink(String stink) {
		this.stink = stink;
	}

	public String getGenerous() {
		return generous;
	}

	public void setGenerous(String generous) {
		this.generous = generous;
	}

	public String getLock() {
		return lock;
	}

	public void setLock(String lock) {
		this.lock = lock;
	}

	public String getInstallationDateTime() {
		return installationDateTime;
	}

	public void setInstallationDateTime(String installationDateTime) {
		this.installationDateTime = installationDateTime;
	}

}
