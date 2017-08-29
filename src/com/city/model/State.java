package com.city.model;

public class State {

	public String stateCode;
	public String stateName;

	public State(String stateCode, String stateName) {
		this.stateCode = stateCode;
		this.stateName = stateName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public String getStateName() {
		return stateName;
	}

}
