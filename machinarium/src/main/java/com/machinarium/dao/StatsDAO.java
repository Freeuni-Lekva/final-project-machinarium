package com.machinarium.dao;

import com.machinarium.model.userBin.Stats;

public interface StatsDAO {
	Stats getStats(String userName);

	boolean incrFirstCount(String userName);
	boolean incrSecondCount(String userName);
	boolean incrThirdCount(String userName);
	boolean incrLoseCount(String userName);

}
