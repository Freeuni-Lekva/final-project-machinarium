package com.machinarium.dao;

import com.machinarium.model.globals.ID;
import com.machinarium.model.history.Reward;

import java.util.List;
import java.util.Map;

public interface RewardDAO {
	Reward getReward(String userName, ID rewardID);
	List<Reward> getRewards(String userName);
	boolean addReward(String userName, Map<String, Integer> rewards);

}
