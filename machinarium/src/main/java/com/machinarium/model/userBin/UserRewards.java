package com.machinarium.model.userBin;

import java.util.Map;

public class UserRewards {
	String userName;
	Map<String, Integer> rewards;

	public UserRewards(String userName, Map<String, Integer> rewards) {
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
