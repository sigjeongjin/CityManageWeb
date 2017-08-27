package com.city.model;

import java.util.ArrayList;
import java.util.List;

public class PushListJSON extends Result {

	private List<PushInfo> pushHistoryList = new ArrayList<PushInfo>();

	public List<PushInfo> getPushHistoryList() {
		return pushHistoryList;
	}

	public void setPushHistoryList(List<PushInfo> pushHistoryList) {
		this.pushHistoryList = pushHistoryList;
	}

}
