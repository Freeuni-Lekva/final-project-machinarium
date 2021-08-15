package com.machinarium.dao.implementation;

import com.machinarium.dao.ConnectionPool;
import com.machinarium.dao.GameDAO;
import com.machinarium.model.car.Car;
import com.machinarium.utility.common.ID;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class GameDAOClass implements GameDAO {
    private final String GAMES_TABLE = "games";
    private final String USER_GAME_TABLE = "user_game";
    private final String USERS_TABLE = "users";
    private ConnectionPool connectionPool;
    public GameDAOClass (ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }
    @Override
    public String getGameHost(ID gameID) {
        Connection con = connectionPool.acquireConnection();
        String userName = null;
        ID userID = null;
        String getGameHostQuery = "SELECT user_host_id FROM " + GAMES_TABLE + " WHERE game_id = " + gameID.getID() +";";
        try {
            Statement getGameHostStat = con.createStatement();
            ResultSet res = getGameHostStat.executeQuery(getGameHostQuery);
            if(res.next()){
                userID = ID.of(res.getInt("user_host_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String getUserNameQuery = "SELECT user_name FROM " + USERS_TABLE + " WHERE id = " + userID.getID() + ";";
        try {
            Statement getUserNameStat = con.createStatement();
            ResultSet res = getUserNameStat.executeQuery(getUserNameQuery);
            if(res.next()){
                userName = res.getString("user_name");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return userName;
    }

    @Override
    public List<String> getGameUsers(ID gameID) {
        Connection con = connectionPool.acquireConnection();
        connectionPool.releaseConnection(con);
        return null;
    }

    @Override
    public ID getActiveGame(String userName) {
        Connection con = connectionPool.acquireConnection();
        connectionPool.releaseConnection(con);
        return null;
    }

    @Override
    public List<ID> getAllActiveGames() {
        Connection con = connectionPool.acquireConnection();
        connectionPool.releaseConnection(con);
        return null;
    }

    @Override
    public String getGameStage(ID gameID) {
        Connection con = connectionPool.acquireConnection();
        connectionPool.releaseConnection(con);
        return null;
    }

    @Override
    public Car getUserChosenCar(String userName, ID gameID) {
        Connection con = connectionPool.acquireConnection();
        connectionPool.releaseConnection(con);
        return null;
    }

    @Override
    public ID addGame(String hostName) {
        Connection con = connectionPool.acquireConnection();
        connectionPool.releaseConnection(con);
        return null;
    }

    @Override
    public boolean addUser(ID gameID, String userName) {
        Connection con = connectionPool.acquireConnection();
        connectionPool.releaseConnection(con);
        return false;
    }
}
