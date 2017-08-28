package com.city.model;

import java.util.ArrayList;
import java.util.List;

public class PushResultListJSON extends Result {

	private List<PushResultInfo> pushHistoryList = new ArrayList<PushResultInfo>();

	public List<PushResultInfo> getPushHistoryList() {
		return pushHistoryList;
	}

	public void setPushHistoryList(List<PushResultInfo> pushHistoryList) {
		this.pushHistoryList = pushHistoryList;
	}

}
