package com.machinarium.dao.implementation;

import com.machinarium.dao.ConnectionPool;
import com.machinarium.dao.UserDAO;
import com.machinarium.model.user.User;
import com.machinarium.utility.EncryptedPassword;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


class UserDAOClass implements UserDAO {
    private final String  USERS_TABLE = "users";
    private ConnectionPool connectionPool;
    public UserDAOClass(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    @Override
    public User getUser(String userName) {
        Connection con = connectionPool.acquireConnection();
        String selectUserQuery = "SELECT * FROM " + USERS_TABLE + "\n"
                                + "WHERE user_name = " + userName + ";";
        User user = null;
        try {
            Statement getUserStat = con.createStatement();
            ResultSet res =  getUserStat.executeQuery(selectUserQuery);
            if(res.next()){
                user = new User(res.getString("user_name"),
                        EncryptedPassword.of(res.getString("user_password")),
                        res.getString("mail"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        connectionPool.releaseConnection(con);
        return user;
    }

    @Override
    public boolean addUser(String userName, EncryptedPassword encryptedPassword, String email) {
        Connection con = connectionPool.acquireConnection();
        boolean addBoolean = false;
        String addUserQuery = "INSERT INTO " + USERS_TABLE + "\n"
                            + "VALUES (" + userName + "," + encryptedPassword + "," + email + ");";
        try {
            Statement addUserStat = con.createStatement();
            if(addUserStat.executeUpdate(addUserQuery) > 0){
                addBoolean = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return addBoolean;
    }

    @Override
    public boolean updatePassword(String userName, EncryptedPassword newEncryptedPassword) {
        Connection con = connectionPool.acquireConnection();
        boolean updatePasswordBoolean = false;
        String updatePasswordQuery = "UPDATE " + USERS_TABLE + "\n"
                                    + "SET user_password = " + newEncryptedPassword + "\n"
                                    + "WHERE user_name = " + userName + ";" ;
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
                                + "SET mail = " + newEmail + "\n"
                                + "WHERE user_name = " + userName + ";" ;
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
        try {
            Statement getAllUsersStat = con.createStatement();
            ResultSet res = getAllUsersStat.executeQuery(getAllUsersQuery);
            while (res.next()){
                User user = new User(res.getString("user_name"),
                        EncryptedPassword.of(res.getString("password")),
                        res.getString("mail"));
                allUsers.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        connectionPool.releaseConnection(con);
        return allUsers;
    }
}
