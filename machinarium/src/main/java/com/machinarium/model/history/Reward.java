package com.machinarium.model.history;

import com.machinarium.utility.common.ID;
import java.util.Map;

public class Reward {
	private final String rewardName;
	private final ID iD;
	private final Map<String, Integer> items;


	public Reward(String userName, ID iD, Map<String, Integer> items) {
		this.rewardName = userName;
		this.iD = iD;
		this.items = items;
	}


	public ID getID() {
		return iD;
	}

	public String getRewardName() {
		return rewardName;
	}

	public Map<String, Integer> getItems() {
		return items;
	}

}
