package com.city.model;

public class Push {
	
	private String pushId;
	private String sensorId;
	private String pushContents;
	private String pushSendTime;
	private String manageId;
	
	public Push() { 
		
	}
	
	public Push(String pushId, String sensorId, String pushContents, String pushSendTime, String manageId) {
		super();
		this.pushId = pushId;
		this.sensorId = sensorId;
		this.pushContents = pushContents;
		this.pushSendTime = pushSendTime;
		this.manageId = manageId;
	}

	public String getPushId() {
		return pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
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

	public String getManageId() {
		return manageId;
	}

	public void setManageId(String manageId) {
		this.manageId = manageId;
	}
}
