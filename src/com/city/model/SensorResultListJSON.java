package com.city.model;

import java.util.ArrayList;
import java.util.List;

public class SensorResultListJSON extends Result{
	private List<SensorResultInfo> sensorList = new ArrayList<SensorResultInfo>();

	public List<SensorResultInfo> getSensorList() {
		return sensorList;
	}

	public void setSensorList(List<SensorResultInfo> sensorList) {
		this.sensorList = sensorList;
	}
}
