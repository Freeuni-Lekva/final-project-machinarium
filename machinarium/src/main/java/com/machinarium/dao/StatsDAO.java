package com.machinarium.dao;

import com.machinarium.model.userBin.UserStats;

public interface StatsDAO {
	boolean updateStats(String userName, int firstCountIncr, int secondCountIncr,
						                 int thirdCountIncr, int loseCountIncr);
	UserStats getStats(String userName);
}
