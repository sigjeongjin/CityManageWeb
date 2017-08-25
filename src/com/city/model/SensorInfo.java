package com.city.model;

import java.util.Date;

public class SensorInfo {

	private String sensorId;				// 센서ID
	private String manageId;				// 관리ID
	private String sensorInfo;				// 센서 이상상태
	private Date installationDatetime;		// 설치 날짜
	private String sensorType;				// 센서 타입
	private String operationStatus;			// 센서 동작상태
	private String sensorNoticeStandard;	// push 알림 기준

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

	public String getSensorType() {
		return sensorType;
	}

	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
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
