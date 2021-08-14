package com.machinarium.model.history;

import com.machinarium.utility.common.ID;
import java.util.Map;

public class Reward {
	private final String userName;
	private final ID iD;
	private final Map<String, Integer> items;


	public Reward(String userName, ID iD, Map<String, Integer> items) {
		this.userName = userName;
		this.iD = iD;
		this.items = items;
	}


	public ID getID() {
		return iD;
	}

	public String getUserName() {
		return userName;
	}

	public Map<String, Integer> getItems() {
		return items;
	}

}
