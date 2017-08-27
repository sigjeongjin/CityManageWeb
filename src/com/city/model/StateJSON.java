package com.city.model;

import java.util.ArrayList;
import java.util.List;


public class StateJSON extends Result {
	private List<State> state = new ArrayList<State>();

	public List<State> getState() {
		return state;
	}

	public void setState(List<State> state) {
		this.state = state;
	}

	
	

}
