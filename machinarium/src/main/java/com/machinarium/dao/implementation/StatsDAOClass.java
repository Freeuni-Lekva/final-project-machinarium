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
    private String USER_STATS_TABLE = "user_statistics";
    private String USER_RESULTS_VIEW = "see_user_results";
    private ConnectionPool connectionPool;

    public StatsDAOClass(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }
    @Override
    public Stats getStats(String userName) {
        Connection con = connectionPool.acquireConnection();
        String getStatsQuery = "SELECT * FROM " + USER_RESULTS_VIEW + "\n"
                            + "WHERE user_name = '" + userName + "';";
        Stats userStats = null;
        try {
            Statement getStatsStat = con.createStatement();
            ResultSet res = getStatsStat.executeQuery(getStatsQuery);
            if(res.next()){
                userStats = new Stats(userName, res.getInt("first_count"),
                                                res.getInt("second_count"),
                                                res.getInt("third_count"),
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
        Connection con = connectionPool.acquireConnection();
        ID userID = null;
        int firsCount = 0;
        String seeFirstCountQuery = "SELECT * FROM " + USER_RESULTS_VIEW + "\n"
                                    + "WHERE user_name = '" + userName + "';";
        try {
            Statement seeFirstCountStat = con.createStatement();
            ResultSet res = seeFirstCountStat.executeQuery(seeFirstCountQuery);
            if(res.next()){
                userID = new ID(res.getInt("user_id"));
                firsCount = res.getInt("first_count");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        boolean incrFirstCountBoolean = false;
        String incrFirstCountQuery = "UPDATE " + USER_STATS_TABLE
                            + " SET first_count = " + (firsCount + 1)
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
        Connection con = connectionPool.acquireConnection();
        ID userID = null;
        int secondCount = 0;
        String seeSecondCountQuery = "SELECT * FROM " + USER_RESULTS_VIEW + "\n"
                                + "WHERE user_name = '" + userName + "';";
        try {
            Statement seeSecondCountStat = con.createStatement();
            ResultSet res = seeSecondCountStat.executeQuery(seeSecondCountQuery);
            if(res.next()){
                userID = new ID(res.getInt("user_id"));
                secondCount = res.getInt("second_count");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        boolean incrSecondCountBoolean = false;
        String incrSecondCountQuery = "UPDATE " + USER_STATS_TABLE
                            + " SET second_count = " + (secondCount + 1)
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
        Connection con = connectionPool.acquireConnection();
        ID userID = null;
        int thirdCount = 0;
        String seeThirdCountQuery = "SELECT * FROM " + USER_RESULTS_VIEW + "\n"
                                + "WHERE user_name = '" + userName + "';";
        try {
            Statement seeThirdCountStat = con.createStatement();
            ResultSet res = seeThirdCountStat.executeQuery(seeThirdCountQuery);
            if(res.next()){
                userID = new ID(res.getInt("user_id"));
                thirdCount = res.getInt("third_count");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        boolean incrThirdCountBoolean = false;
        String incrThirdCountQuery = "UPDATE " + USER_STATS_TABLE
                            + " SET third_count = " + (thirdCount+ 1)
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
        Connection con = connectionPool.acquireConnection();
        ID userID = null;
        int loseCount = 0;
        String seeLoseCountQuery = "SELECT * FROM " + USER_RESULTS_VIEW + "\n"
                + "WHERE user_name = '" + userName + "';";
        try {
            Statement seeLoseCountStat = con.createStatement();
            ResultSet res = seeLoseCountStat.executeQuery(seeLoseCountQuery);
            if(res.next()){
                userID = new ID(res.getInt("user_id"));
                loseCount = res.getInt("lose_count");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        boolean incrLoseCountBoolean = false;
        String incrLoseCountQuery = "UPDATE " + USER_STATS_TABLE
                + " SET lose_count = " + (loseCount + 1)
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
