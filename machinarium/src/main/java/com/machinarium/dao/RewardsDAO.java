package com.machinarium.dao;

import com.machinarium.model.userBin.UserRewards;

import java.util.Map;

public interface RewardsDAO {
	boolean addRewards(String userName, Map<String, Integer> rewards);
	UserRewards getRewards(String userName);
}
