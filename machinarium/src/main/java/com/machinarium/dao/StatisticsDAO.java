package com.machinarium.dao;

import com.machinarium.model.history.Statistics;

public interface StatisticsDAO {
	Statistics getStatistics(String userName);

	boolean incrementFirstCount(String userName);
	boolean incrementSecondCount(String userName);
	boolean incrementThirdCount(String userName);
	boolean incrementLoseCount(String userName);

}
