package com.machinarium.dao;

import com.machinarium.utility.common.ID;
import com.machinarium.model.history.Reward;

import java.util.List;
import java.util.Map;

public interface RewardDAO {

	Reward getReward(ID rewardID);

	List<Reward> getRewards(String userName);
       /**
	 * Adds a reward to the specified user.
	 *
	 * @param userName The username of the receiver of the reward.
	 * @param rewards The contents of the reward as a mapping of {@link Item} {@link ID}s to amounts.
	 * @return True if the reward was was successfully added.
	 */
	boolean addReward(String userName, Map<ID, Integer> rewards);
}
