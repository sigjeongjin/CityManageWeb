package com.city.model;

public class Push {

	private String manageId;
	private String locationName;
	private String pushContents;
	private String pushSendTime;
	private String installationDateTime;
	private String location;

	public Push(String manageId, String locationName, String pushContents, String pushSendTime,
			String installationDateTime, String location) {
		super();
		this.manageId = manageId;
		this.locationName = locationName;
		this.pushContents = pushContents;
		this.pushSendTime = pushSendTime;
		this.installationDateTime = installationDateTime;
		this.location = location;
	}

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

	public String getPushContents() {
		return pushContents;
	}

	public void setPushContents(String pushContents) {
		this.pushContents = pushContents;
	}

	public String getPushSendTime() {
		return pushSendTime;
	}

	public void setPushSendTime(String pushSendTime) {
		this.pushSendTime = pushSendTime;
	}

	public String getInstallationDateTime() {
		return installationDateTime;
	}

	public void setInstallationDateTime(String installationDateTime) {
		this.installationDateTime = installationDateTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
