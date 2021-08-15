package com.machinarium.common;

import com.machinarium.dao.ConnectionPool;
import com.machinarium.dao.implementation.BlockingConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestDBManager {

    public static void resetDB() {
        ConnectionPool connectionPool = BlockingConnectionPool.getInstance();
        Connection con = connectionPool.acquireConnection();

        try {
            Statement getUserStatement = con.createStatement();

            List<String> allQueries = new ArrayList<>();

            allQueries.add("SET FOREIGN_KEY_CHECKS = 0;");
            allQueries.addAll(List.of(
                    "user_order", "order_item", "orders", "user_game", "game_results",
                    "reward_item", "rewards", "user_statistics", "garage_item", "garage_car",
                    "car_parts", "cars", "user_garage", "fusion_tools", "games", "garages", "users"
            ).stream().map(table_name -> "TRUNCATE TABLE " + table_name + ";").collect(Collectors.toList()));
            allQueries.add("SET FOREIGN_KEY_CHECKS = 1;");

            for (String query : allQueries) {
                getUserStatement.executeUpdate(query);
            }

            connectionPool.releaseConnection(con);
//			connectionPool.close();  //--

        } catch (SQLException e) {
            e.printStackTrace(); }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
