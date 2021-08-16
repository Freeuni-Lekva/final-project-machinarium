package com.machinarium.model.history;

import com.machinarium.utility.common.ID;
import java.util.Map;

public class Reward {
	private final String rewardName;
	private final ID iD;
	private final Map<ID, Integer> items;


	public Reward(String rewardName, ID iD, Map<ID, Integer> items) {
		this.rewardName = rewardName;
		this.iD = iD;
		this.items = items;
	}


	public ID getID() {
		return iD;
	}

	public String getRewardName() {
		return rewardName;
	}

	public Map<ID, Integer> getItems() {
		return items;
	}

}
