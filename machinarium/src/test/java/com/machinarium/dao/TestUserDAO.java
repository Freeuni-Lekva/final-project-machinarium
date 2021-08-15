package com.machinarium.dao;

import com.machinarium.dao.implementation.BlockingConnectionPool;
import com.machinarium.dao.implementation.UserDAOClass;
import com.machinarium.model.user.User;
import com.machinarium.utility.common.Email;
import com.machinarium.utility.common.EncryptedPassword;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestUserDAO {
	private static ConnectionPool connectionPool;
	private UserDAO userDAO;

	@BeforeEach
	public void setup() {
		connectionPool = BlockingConnectionPool.getInstance();
		userDAO = new UserDAOClass(connectionPool);
	}

	@AfterEach
	public void tearDown() {
		try {
			Runtime.getRuntime().exec(
					"mysql --user=\"root\" --database=\"machinarium_database\" --password=\"Data_base1\" < \"create_database_tables.sql\" ;" +
					"mysql --user=\"root\" --database=\"machinarium_database\" --password=\"Data_base1\" < \"create_database_views.sql\" ;" +
					"mysql --user=\"root\" --database=\"machinarium_database\" --password=\"Data_base1\" < \"initial_database_state.sql\"",
					null,
					new File("./src/main/java/com/machinarium/scripts"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@AfterAll
	public static void end() {
		try {
			connectionPool.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Test
	public void Test_0() {
		assertEquals(null, userDAO.getUser("bla"));
		assertFalse(userExists("bla"));
	}

	@Test
	public void Test_1() {
		if (! userExists("gelasha")) {
			assertTrue(userDAO.addUser("gelasha", EncryptedPassword.of("1234#gelasha"), "gelasha18@freeuni.edu.ge"));
		}
		assertNotEquals(null, userDAO.getUser("gelasha"));


		assertTrue(userDAO.updatePassword("gelasha", EncryptedPassword.of("1111@gelasha")));
		assertTrue(userDAO.updatePassword("gelasha", EncryptedPassword.of("1234#gelasha")));
	}


	@Test
	public void Test_2() {
		if (! userExists("luka")) {
			assertTrue(userDAO.addUser("luka", EncryptedPassword.of("1234#Luka"), "lgela18@freeuni.edu.ge"));
		}
		assertTrue(userDAO.updateEmail("luka", "lgela18@freeuni.edu.ge"));
		assertNotEquals(null, userDAO.getUser("luka"));

		assertEquals("luka", userDAO.getUser(Email.of("lgela18@freeuni.edu.ge")).getUserName());
		assertTrue(userDAO.updateEmail("luka", "lgela18@gmail.com"));
		assertEquals("luka", userDAO.getUser(Email.of("lgela18@gmail.com")).getUserName());


		assertEquals(2, userDAO.getAllUsers().size());
	}


	private boolean userExists(String userName) {
		List<User> users = userDAO.getAllUsers();
		for (User user: users) {
			if (user.getUserName().equals(userName)) { return true; }
		}
		return false;
	}

}
