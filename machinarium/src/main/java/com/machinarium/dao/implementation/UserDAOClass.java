package com.machinarium.dao.implementation;

import com.machinarium.dao.ConnectionPool;
import com.machinarium.dao.UserDAO;
import com.machinarium.model.user.User;
import com.machinarium.utility.common.ConfiguredLogger;
import com.machinarium.utility.common.Email;
import com.machinarium.utility.common.EncryptedPassword;
import com.machinarium.utility.common.ID;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserDAOClass implements UserDAO {

    private static final Logger logger = ConfiguredLogger.getLogger("UserDAO");

    private final String USERS_TABLE = "users";
    private final String USER_STATISTIC_TABLE = "user_statistics";
    private final String USER_GARAGE_TABLE ="user_garage";
    private final ConnectionPool connectionPool;

    public UserDAOClass(ConnectionPool connectionPool){
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

    private ID addGarage(ID userID, Connection con){
        String garageNameGenerator = "garage_" + userID.getID();
        String addUserGarageQuery = "INSERT INTO garages(garage_name) VALUES " + garageNameGenerator + ";";
        ID garageID = null;
        try {
            Statement addUserGarageStat = con.createStatement();
            if (addUserGarageStat.executeUpdate(addUserGarageQuery) > 0){
                String getGarageIDQuery = "SELECT id FROM garages WHERE garage_name = '" + garageNameGenerator + "';";
                Statement getGarageIDStat = con.createStatement();
                ResultSet res = getGarageIDStat.executeQuery(getGarageIDQuery);
                res.next();
                garageID = new ID(res.getInt("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return garageID;
    }
    private boolean addUserGarage(ID userID, ID garageID, Connection con){
        boolean addUserGarageBoolean = false;
        String addUserGarageQuery = "INSERT INTO " + USER_GARAGE_TABLE + "(user_id, garage_id)\n"
                                    + "VALUES (" + userID.getID() + ", " + garageID.getID() + ");";
        try {
            Statement addUserGarageStat = con.createStatement();
            if(addUserGarageStat.executeUpdate(addUserGarageQuery) > 0)
                addUserGarageBoolean = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return addUserGarageBoolean;
    }
    @Override
    public User getUser(String userName) {

        Connection con = connectionPool.acquireConnection();
        String selectUserQuery = "SELECT * FROM " + USERS_TABLE + "\n"
                               + "WHERE user_name = '" + userName + "';";

        logger.log(Level.FINE, "Query: " + selectUserQuery);

        User user = null;
        try {
            Statement getUserStat = con.createStatement();
            ResultSet res =  getUserStat.executeQuery(selectUserQuery);
            if(res.next()){
                user = new User(res.getString("user_name"),
                        EncryptedPassword.from(res.getString("user_password")),
                        Email.of(res.getString("mail")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        connectionPool.releaseConnection(con);
        return user;
    }

    @Override
    public User getUser(Email email) { // TODO: Change this implementation
        return getAllUsers().stream().filter(user -> user.getEmail() == email).findFirst().orElse(null);
    }

    @Override
    public boolean addUser(String userName, EncryptedPassword encryptedPassword, String email) {

        Connection con = connectionPool.acquireConnection();
        boolean addBoolean = false;
        String addUserQuery = "INSERT INTO " + USERS_TABLE + " (user_name, user_password, mail)"
                            + "VALUES ('" + userName + "', '" + encryptedPassword + "', '" + email + "');";

        logger.log(Level.FINE, "Query: " + addUserQuery);

        try {
            Statement addUserStat = con.createStatement();
            if(addUserStat.executeUpdate(addUserQuery) > 0){
                addBoolean = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(addBoolean){
            ID userID = getUserID(userName, con);
            String addUserInStatisticsQuery = "INSERT INTO " + USER_STATISTIC_TABLE + "(user_id)\n"
                                            + "VALUES (" + userID.getID() + ");";
            try {
                Statement addUserInStatisticsStat = con.createStatement();
                addUserInStatisticsStat.executeUpdate(addUserInStatisticsQuery);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ID garageID = addGarage(userID, con);
            addBoolean = addUserGarage(userID, garageID, con);
        }
        connectionPool.releaseConnection(con);
        return addBoolean;
    }

    @Override
    public boolean updatePassword(String userName, EncryptedPassword newEncryptedPassword) {

        Connection con = connectionPool.acquireConnection();
        boolean updatePasswordBoolean = false;
        String updatePasswordQuery = "UPDATE " + USERS_TABLE + "\n"
                                   + "SET user_password = '" + newEncryptedPassword + "'\n"
                                   + "WHERE user_name = '" + userName + "';" ;

        logger.log(Level.FINE, "Query: " + updatePasswordQuery);

        try {
            Statement updatePasswordStat = con.createStatement();
            if(updatePasswordStat.executeUpdate(updatePasswordQuery) > 0){
                updatePasswordBoolean = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return updatePasswordBoolean;
    }

    @Override
    public boolean updateEmail(String userName, String newEmail) {

        Connection con = connectionPool.acquireConnection();
        boolean updateEmailBoolean = false;
        String updateEmailQuery = "UPDATE " + USERS_TABLE + "\n"
                                + "SET mail = '" + newEmail + "'\n"
                                + "WHERE user_name = '" + userName + "';" ;

        logger.log(Level.FINE, "Query: " + updateEmailQuery);

        try {
            Statement updateEmailStat = con.createStatement();
            if(updateEmailStat.executeUpdate(updateEmailQuery) > 0){
                updateEmailBoolean = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return updateEmailBoolean;
    }

    @Override
    public List<User> getAllUsers() {

        Connection con = connectionPool.acquireConnection();
        String getAllUsersQuery = "SELECT * FROM " + USERS_TABLE;
        List<User> allUsers = new ArrayList<>();

        logger.log(Level.FINE, "Query: " + getAllUsersQuery);

        try {
            Statement getAllUsersStat = con.createStatement();
            ResultSet res = getAllUsersStat.executeQuery(getAllUsersQuery);
            while (res.next()){
                User user = new User(res.getString("user_name"),
                        EncryptedPassword.from(res.getString("user_password")),
                        Email.of(res.getString("mail")));
                allUsers.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        connectionPool.releaseConnection(con);
        return allUsers;
    }
}
