package com.machinarium.common;

import com.machinarium.dao.ConnectionPool;
import com.machinarium.dao.implementation.BlockingConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestDBManager {

	public static void resetDB() {
		ConnectionPool connectionPool = BlockingConnectionPool.getInstance();
		Connection con = connectionPool.acquireConnection();

		try {
			Statement getUserStatement = con.createStatement();

			List<String> allQueries = new ArrayList<>();
			allQueries.addAll(Arrays.asList("SET FOREIGN_KEY_CHECKS = 0; ", "TRUNCATE TABLE user_order; ", "SET FOREIGN_KEY_CHECKS = 1; "));
			allQueries.addAll(Arrays.asList("SET FOREIGN_KEY_CHECKS = 0; ", "TRUNCATE TABLE order_item; ", "SET FOREIGN_KEY_CHECKS = 1; "));
			allQueries.addAll(Arrays.asList("SET FOREIGN_KEY_CHECKS = 0; ", "TRUNCATE TABLE orders; ", "SET FOREIGN_KEY_CHECKS = 1; "));
			allQueries.addAll(Arrays.asList("SET FOREIGN_KEY_CHECKS = 0; ", "TRUNCATE TABLE user_game; ", "SET FOREIGN_KEY_CHECKS = 1; "));
			allQueries.addAll(Arrays.asList("SET FOREIGN_KEY_CHECKS = 0; ", "TRUNCATE TABLE game_results; ", "SET FOREIGN_KEY_CHECKS = 1; "));
			allQueries.addAll(Arrays.asList("SET FOREIGN_KEY_CHECKS = 0; ", "TRUNCATE TABLE reward_item; ", "SET FOREIGN_KEY_CHECKS = 1; "));
			allQueries.addAll(Arrays.asList("SET FOREIGN_KEY_CHECKS = 0; ", "TRUNCATE TABLE rewards; ", "SET FOREIGN_KEY_CHECKS = 1; "));
			allQueries.addAll(Arrays.asList("SET FOREIGN_KEY_CHECKS = 0; ", "TRUNCATE TABLE user_statistics; ", "SET FOREIGN_KEY_CHECKS = 1; "));
			allQueries.addAll(Arrays.asList("SET FOREIGN_KEY_CHECKS = 0; ", "TRUNCATE TABLE garage_item; ", "SET FOREIGN_KEY_CHECKS = 1; "));
			allQueries.addAll(Arrays.asList("SET FOREIGN_KEY_CHECKS = 0; ", "TRUNCATE TABLE garage_car; ", "SET FOREIGN_KEY_CHECKS = 1; "));
			allQueries.addAll(Arrays.asList("SET FOREIGN_KEY_CHECKS = 0; ", "TRUNCATE TABLE car_parts; ", "SET FOREIGN_KEY_CHECKS = 1; "));
			allQueries.addAll(Arrays.asList("SET FOREIGN_KEY_CHECKS = 0; ", "TRUNCATE TABLE cars; ", "SET FOREIGN_KEY_CHECKS = 1; "));
			allQueries.addAll(Arrays.asList("SET FOREIGN_KEY_CHECKS = 0; ", "TRUNCATE TABLE user_garage; ", "SET FOREIGN_KEY_CHECKS = 1; "));
			allQueries.addAll(Arrays.asList("SET FOREIGN_KEY_CHECKS = 0; ", "TRUNCATE TABLE fusion_tools; ", "SET FOREIGN_KEY_CHECKS = 1; "));
			allQueries.addAll(Arrays.asList("SET FOREIGN_KEY_CHECKS = 0; ", "TRUNCATE TABLE games; ", "SET FOREIGN_KEY_CHECKS = 1; "));
			allQueries.addAll(Arrays.asList("SET FOREIGN_KEY_CHECKS = 0; ", "TRUNCATE TABLE garages; ", "SET FOREIGN_KEY_CHECKS = 1; "));
			allQueries.addAll(Arrays.asList("SET FOREIGN_KEY_CHECKS = 0; ", "TRUNCATE TABLE users; ", "SET FOREIGN_KEY_CHECKS = 1; "));


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
