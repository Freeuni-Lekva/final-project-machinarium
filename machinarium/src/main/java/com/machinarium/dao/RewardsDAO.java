package com.machinarium.dao;

import com.machinarium.model.userBin.Rewards;

import java.util.Map;

public interface RewardsDAO {
	Rewards getRewards(String userName);
	boolean addRewards(String userName, Map<String, Integer> rewards);

}
