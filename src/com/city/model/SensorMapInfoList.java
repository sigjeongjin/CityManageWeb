package com.city.model;

import java.util.ArrayList;
import java.util.List;

public class SensorMapInfoList extends Result {
	private List<SensorInfo> sensorMapInfoList = new ArrayList<SensorInfo>();

	public List<SensorInfo> getSensorMapInfoList() {
		return sensorMapInfoList;
	}

	public void setSensorMapInfoList(List<SensorInfo> sensorMapInfoList) {
		this.sensorMapInfoList = sensorMapInfoList;
	}
}
