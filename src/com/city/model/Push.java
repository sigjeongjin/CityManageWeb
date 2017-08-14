package com.city.model;

public class Push {
	
	private String sensorId;
	private String pushContents;
	private String pushSendTime;
	
	public Push() { 
		
	}
	public Push(String sensorId, String pushContents, String pushSendTime){ 
		this.sensorId = sensorId;
		this.pushContents = pushContents;
		this.pushSendTime = pushSendTime;
	}
	public String getSensorId() {
		return sensorId;
	}
	public String getPushContents() {
		return pushContents;
	}
	public String getPushSendTime() {
		return pushSendTime;
	}
	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}
	public void setPushContents(String pushContents) {
		this.pushContents = pushContents;
	}
	public void setPushSendTime(String pushSendTime) {
		this.pushSendTime = pushSendTime;
	}
	

}
