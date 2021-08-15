package com.machinarium.dao;

import com.machinarium.utility.common.ID;
import com.machinarium.model.history.Reward;

import java.util.List;
import java.util.Map;

public interface RewardDAO {
	Reward getReward(String userName, ID rewardID);
	List<Reward> getRewards(String userName);
	boolean addReward(String rewardName, Map<String, Integer> rewards);

}
