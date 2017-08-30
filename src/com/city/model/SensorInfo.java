package com.city.model;

import java.util.Date;

public class SensorInfo {

	private String sensorId;
	private String manageId;
	private String sensorInfo;
	private Date installationDatetime;
	private String manageType;
	private String operationStatus;
	private String sensorNoticeStandard;
	private String sensorType;
	
	public SensorInfo(String sensorId, String sensorInfo, String manageId, Date installationDatetime
			, String manageType, String operationStatus, String sensorNoticeStandard, String sensorType) {
		this.sensorId = sensorId;
		this.sensorInfo = sensorInfo;	
		this.manageId = manageId;
		this.installationDatetime = installationDatetime;
		this.manageType = manageType;
		this.operationStatus = operationStatus;
		this.sensorNoticeStandard = sensorNoticeStandard;
		this.sensorType = sensorType;
		
	}

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public String getManageId() {
		return manageId;
	}

	public void setManageId(String manageId) {
		this.manageId = manageId;
	}

	public String getSensorInfo() {
		return sensorInfo;
	}

	public void setSensorInfo(String sensorInfo) {
		this.sensorInfo = sensorInfo;
	}

	public Date getInstallationDatetime() {
		return installationDatetime;
	}

	public void setInstallationDatetime(Date installationDatetime) {
		this.installationDatetime = installationDatetime;
	}

	public String getManageType() {
		return manageType;
	}

	public void setManageType(String manageType) {
		this.manageType = manageType;
	}

	public String getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}

	public String getSensorNoticeStandard() {
		return sensorNoticeStandard;
	}

	public void setSensorNoticeStandard(String sensorNoticeStandard) {
		this.sensorNoticeStandard = sensorNoticeStandard;
	}
}
