package com.machinarium.dao.implementation;

import com.machinarium.dao.ConnectionPool;
import com.machinarium.dao.StatsDAO;
import com.machinarium.model.history.Stats;
import com.machinarium.utility.common.ID;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatsDAOClass implements StatsDAO {
    private String USERS_TABLE = "users";
    private String USER_STATS_TABLE = "user_statistics";
    private ConnectionPool connectionPool;
    public StatsDAOClass(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }
    private ID getUserID(String userName, Connection con){
        ID id = null;
        String getUserIDQuery = "SELECT id FROM " + USERS_TABLE + "\n"
                + "WHERE user_name = '" + userName + "';";
        try {
            Statement getUserIDStat = con.createStatement();
            ResultSet res = getUserIDStat.executeQuery(getUserIDQuery);
            if(res.next()){
                id = new ID(res.getInt("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }
    @Override
    public Stats getStats(String userName) {
        Connection con = connectionPool.acquireConnection();
        ID userID = getUserID(userName, con);
        String getStatsQuery = "SELECT * FROM " + USER_STATS_TABLE +
                            " WHERE user_id = " + userID.getID() + ";";
        Stats userStats = null;
        try {
            Statement getStatsStat = con.createStatement();
            ResultSet res = getStatsStat.executeQuery(getStatsQuery);
            if(res.next()){
                userStats = new Stats(userName, res.getInt("first_count"),
                        res.getInt("second_count"),res.getInt("third_count"),
                        res.getInt("lose_count"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return userStats;
    }

    @Override
    public boolean incrFirstCount(String userName) {
        Stats currStats = getStats(userName);
        Connection con = connectionPool.acquireConnection();
        ID userID = getUserID(userName, con);
        boolean incrFirstCountBoolean = false;
        String incrFirstCountQuery = "UPDATE " + USER_STATS_TABLE
                            + " SET first_count = " + (currStats.getFirstCount() + 1)
                            + " WHERE user_id = " + userID.getID() + ";";
        try {
            Statement incrFirstCountStat = con.createStatement();
            if(incrFirstCountStat.executeUpdate(incrFirstCountQuery) > 0){
                incrFirstCountBoolean = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return incrFirstCountBoolean;
    }

    @Override
    public boolean incrSecondCount(String userName) {
        Stats currStats = getStats(userName);
        Connection con = connectionPool.acquireConnection();
        ID userID = getUserID(userName, con);
        boolean incrSecondCountBoolean = false;
        String incrSecondCountQuery = "UPDATE " + USER_STATS_TABLE
                + " SET second_count = " + (currStats.getSecondCount() + 1)
                + " WHERE user_id = " + userID.getID() + ";";
        try {
            Statement incrSecondCountStat = con.createStatement();
            if(incrSecondCountStat.executeUpdate(incrSecondCountQuery) > 0){
                incrSecondCountBoolean = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return incrSecondCountBoolean;
    }

    @Override
    public boolean incrThirdCount(String userName) {
        Stats currStats = getStats(userName);
        Connection con = connectionPool.acquireConnection();
        ID userID = getUserID(userName, con);
        boolean incrThirdCountBoolean = false;
        String incrThirdCountQuery = "UPDATE " + USER_STATS_TABLE
                + " SET third_count = " + (currStats.getThirdCount() + 1)
                + " WHERE user_id = " + userID.getID() + ";";
        try {
            Statement incrThirdCountStat = con.createStatement();
            if(incrThirdCountStat.executeUpdate(incrThirdCountQuery) > 0){
                incrThirdCountBoolean = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return incrThirdCountBoolean;
    }

    @Override
    public boolean incrLoseCount(String userName) {
        Stats currStats = getStats(userName);
        Connection con = connectionPool.acquireConnection();
        ID userID = getUserID(userName, con);
        boolean incrLoseCountBoolean = false;
        String incrLoseCountQuery = "UPDATE " + USER_STATS_TABLE
                + " SET lose_count = " + (currStats.getLoseCount() + 1)
                + " WHERE user_id = " + userID.getID() + ";";
        try {
            Statement incrLoseCountStat = con.createStatement();
            if(incrLoseCountStat.executeUpdate(incrLoseCountQuery) > 0){
                incrLoseCountBoolean = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return incrLoseCountBoolean;
    }
}
