package com.machinarium.dao.implementation;

import com.machinarium.dao.ConnectionPool;
import com.machinarium.dao.GameDAO;
import com.machinarium.dao.GarageDAO;
import com.machinarium.model.car.Car;
import com.machinarium.utility.common.ID;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GameDAOClass implements GameDAO {

    private final String GAMES_TABLE = "games";
    private final String USER_GAME_TABLE = "user_game";
    private final String USERS_TABLE = "users";
    private final String GAMES_VIEW = "see_games";
    private final String USER_GAME_VIEW = "see_user_game";
    private final String GAME_RESULTS_TABLE = "game_results";

    private ConnectionPool connectionPool;

    public GameDAOClass (ConnectionPool connectionPool){
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
                id = ID.of(res.getInt("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    @Override
    public String getGameHost(ID gameID) {
        Connection con = connectionPool.acquireConnection();
        String userName = null;

        String getGameHostQuery = "SELECT * FROM " + GAMES_VIEW + " WHERE game_id = " + gameID.getID() +";";
        try {
            Statement getGameHostStat = con.createStatement();
            ResultSet res = getGameHostStat.executeQuery(getGameHostQuery);
            if(res.next()){
                userName = res.getString("user_host_name");
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
        String getGameUsersQuery = "SELECT * FROM " + USER_GAME_VIEW + " WHERE game_id = " + gameID.getID() + ";";
        List<String> gameUsers = new ArrayList<>();
        try {
            Statement getGameUsersStat = con.createStatement();
            ResultSet res = getGameUsersStat.executeQuery(getGameUsersQuery);
            while (res.next()){
                gameUsers.add(res.getString("user_name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        connectionPool.releaseConnection(con);
        return gameUsers;
    }

    @Override
    public ID getActiveGame(String userName) {
        Connection con = connectionPool.acquireConnection();
        String getActiveGameQuery = "SELECT game_id FROM " + USER_GAME_VIEW
                                  + " WHERE user_name = '" + userName + "' AND stage_name IN " + GameStage.getActiveStages();
        ID gameID = null;
        try {
            Statement getActiveGameStatement = con.createStatement();
            ResultSet res = getActiveGameStatement.executeQuery(getActiveGameQuery);
            if(res.next()){
                gameID = ID.of(res.getInt("game_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return gameID;
    }

    @Override
    public List<ID> getAllActiveGames() {
        Connection con = connectionPool.acquireConnection();
        String getAllActiveGamesQuery = "SELECT * FROM " + GAMES_VIEW + " WHERE stage_name IN " + GameStage.getActiveStages();
        List<ID> allActiveGames = new ArrayList<>();
        try {
            Statement getAllActiveGamesStat = con.createStatement();
            ResultSet res = getAllActiveGamesStat.executeQuery(getAllActiveGamesQuery);
            while (res.next()){
                allActiveGames.add(ID.of(res.getInt("game_id")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        connectionPool.releaseConnection(con);
        return allActiveGames;
    }

    @Override
    public GameStage getGameStage(ID gameID) {
        Connection con = connectionPool.acquireConnection();
        String getGameStageQuery = "SELECT * FROM " + GAMES_VIEW + " WHERE game_id = " + gameID.getID() + ";";
        GameStage gameStage = null;
        try {
            Statement getGameStageStat = con.createStatement();
            ResultSet res = getGameStageStat.executeQuery(getGameStageQuery);
            if(res.next()){
                gameStage = GameStage.of(res.getString("stage_name"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return gameStage;
    }

    @Override
    public Car getUserChosenCar(String userName, ID gameID) {
        Connection con = connectionPool.acquireConnection();
        String getUserChosenCarQuery = "SELECT * FROM " + USER_GAME_VIEW
                                     + " WHERE user_name = '" + userName + "' AND game_id = " + gameID.getID() + ";";
        Car car = null;
        try {
            Statement getUserChosenCarStat = con.createStatement();
            ResultSet res = getUserChosenCarStat.executeQuery(getUserChosenCarQuery);
            if(res.next()){
                if(res.getString("car_name") != null){
                    GarageDAO garageDAO = new GarageDAOClass(connectionPool);
                    car = garageDAO.getCar(ID.of(res.getInt("car_id")));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return car;
    }

    @Override
    public ID addGame(String hostName) {
        Connection con = connectionPool.acquireConnection();
        ID userID = getUserID(hostName, con);
        String addGameQuery = "INSERT INTO " + GAMES_TABLE + "(game_stage_id, user_host_id)" +
                        "VALUES (1, " + userID.getID() + ");"; //in lobby
        ID gameID = null;
        try {
            Statement addGameStatement = con.createStatement();
            if (addGameStatement.executeUpdate(addGameQuery, Statement.RETURN_GENERATED_KEYS) > 0){
                ResultSet generatedID = addGameStatement.getGeneratedKeys();
                if(generatedID.next()){
                    gameID = ID.of(generatedID.getInt(1));
                    addUser(gameID, hostName);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return gameID;
    }

    @Override
    public boolean addUser(ID gameID, String userName) {
        Connection con = connectionPool.acquireConnection();
        ID userID = getUserID(userName, con);
        String addUserQuery = "INSERT INTO " + USER_GAME_TABLE + "(user_id, game_id) " +
                                "VALUES (" + userID.getID() + ", " + gameID.getID() + ");";
        return updateQuery(con, addUserQuery);
    }

    @Override
    public boolean updateGameResult(ID gameID, String firstPlaceUser, ID firstPlaceReward,
                                               String secondPlaceUser, ID secondPlaceReward,
                                               String thirdPlaceUser, ID thirdPlaceReward) {
        Connection con = connectionPool.acquireConnection();
        ID firstID = getUserID(firstPlaceUser, con);
        ID secondID = getUserID(secondPlaceUser, con);
        ID thirdID = getUserID(thirdPlaceUser, con);
        String updateGameResultQuery = "INSERT INTO " + GAME_RESULTS_TABLE +
                "(game_id, first_place_id, reward1_id, second_place_id, reward2_id, third_place_id, reward3_id)\n" +
                "VALUES (" + gameID + ", " + firstID + ", " + firstPlaceReward + ", " + secondID + ", "+
                secondPlaceReward + ", " + thirdID + ", " + thirdPlaceReward + ");";
        return updateQuery(con, updateGameResultQuery);
    }

    @Override
    public boolean updateGameStage(ID gameID, GameStage newStage) {
        Connection con = connectionPool.acquireConnection();
        String updateGameStageQuery = "UPDATE " + GAMES_TABLE + " SET game_stage_id = " + newStage.getValue() + "\n" +
                                "WHERE id = " + gameID + ";";
        return updateQuery(con, updateGameStageQuery);
    }

    private boolean updateQuery(Connection con, String updateQuery) {
        boolean updateBoolean = false;
        try {
            Statement updateStat = con.createStatement();
            if (updateStat.executeUpdate(updateQuery) > 0)
                updateBoolean = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return updateBoolean;
    }
}
