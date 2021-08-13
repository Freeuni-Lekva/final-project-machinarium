package com.machinarium.model.history;

import java.util.Map;

public class Rewards {
	private final String userName;
	private final Map<String, Integer> rewards;


	public Rewards(String userName, Map<String, Integer> rewards) {
		this.userName = userName;
		this.rewards = rewards;
	}


	public String getUserName() {
		return userName;
	}

	public Map<String, Integer> getRewards() {
		return rewards;
	}

}
