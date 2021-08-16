package com.machinarium.dao.implementation;

import com.machinarium.dao.ConnectionPool;
import com.machinarium.dao.RewardDAO;
import com.machinarium.model.history.Reward;
import com.machinarium.utility.common.ID;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardDAOClass implements RewardDAO {
    private final String USER_REWARDS_VIEW = "see_user_rewards";
    private final String REWARD_ITEM_TABLE = "reward_item";
    private final String REWARDS_TABLE = "rewards";
    private ConnectionPool connectionPool;

    public RewardDAOClass(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }
    @Override
    public Reward getReward(ID rewardID) {
        Connection con = connectionPool.acquireConnection();
        String getRewardQuery = "SELECT * FROM " + REWARD_ITEM_TABLE +
                                " WHERE reward_id = " + rewardID.getID() + ";";
        String getRewardNameQuery = "SELECT reward_name FROM " + REWARDS_TABLE +
                                " WHERE id = " + rewardID.getID()+ ";";
        String rewardName = null;
        Map<ID, Integer> itemCount = new HashMap<>();
        try {
            Statement getRewardStat = con.createStatement();
            ResultSet res = getRewardStat.executeQuery(getRewardQuery);
            while (res.next()){
                itemCount.put(new ID(res.getInt("item_id")), res.getInt("item_count"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            Statement getRewardNameStat = con.createStatement();
            ResultSet res = getRewardNameStat.executeQuery(getRewardNameQuery);
            if(res.next()){
                rewardName = res.getString("reward_name");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Reward reward = new Reward(rewardName, rewardID, itemCount);
        connectionPool.releaseConnection(con);
        return reward;
    }

    @Override
    public List<Reward> getRewards(String userName) {
        Connection con = connectionPool.acquireConnection();
        String getRewardsQuery = "SELECT * FROM " + USER_REWARDS_VIEW + " WHERE user_name = '" + userName + "';";
        List<Reward> rewards = new ArrayList<>();
        try {
            Statement getRewardsStat = con.createStatement();
            ResultSet res = getRewardsStat.executeQuery(getRewardsQuery);
            while(res.next()){
                Reward r = getReward(new ID(res.getInt("reward_id")));
                rewards.add(r);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return rewards;
    }

}
