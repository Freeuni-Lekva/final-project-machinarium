package com.machinarium.dao;

import com.machinarium.common.TestDBManager;
import com.machinarium.dao.implementation.BlockingConnectionPool;
import com.machinarium.dao.implementation.UserDAOClass;
import com.machinarium.model.user.User;
import com.machinarium.utility.common.Email;
import com.machinarium.utility.common.EncryptedPassword;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestUserDAO {
	private static ConnectionPool connectionPool;
	private static UserDAO userDAO;

	@BeforeAll
	public static void begin() {
		TestDBManager.resetDB();
	}

	@BeforeEach
	public void setup() {
		connectionPool = BlockingConnectionPool.getInstance();
		userDAO = new UserDAOClass(connectionPool);
	}

	@AfterEach
	public void tearDown() {
		TestDBManager.resetDB();
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
	public void addUsers() {
		assertTrue(userDAO.addUser("luka", EncryptedPassword.of("1234#Luka"), "luka@gmail.com"));
		assertTrue(userDAO.addUser("lukaA", EncryptedPassword.of("1234#LukaA"), "lukaA@gmail.com"));
		assertTrue(userDAO.addUser("lukaB", EncryptedPassword.of("1234#LukaB"), "lukaB@gmail.com"));
	}

	@Test
	public void getUserByName_addUser() {
		addUsers();

		assertTrue(userDAO.getUser("luka").getUserName().equals("luka"));
		assertTrue(userDAO.getUser("luka").getPassword().equals(EncryptedPassword.of("1234#Luka")));
		assertTrue(userDAO.getUser("luka").getEmail().equals(Email.of("luka@gmail.com")));

		assertTrue(userDAO.getUser("lukaA").getUserName().equals("lukaA"));
		assertTrue(userDAO.getUser("lukaA").getPassword().equals(EncryptedPassword.of("1234#LukaA")));
		assertTrue(userDAO.getUser("lukaA").getEmail().equals(Email.of("lukaA@gmail.com")));

		assertTrue(userDAO.getUser("lukaB").getUserName().equals("lukaB"));
		assertTrue(userDAO.getUser("lukaB").getPassword().equals(EncryptedPassword.of("1234#LukaB")));
		assertTrue(userDAO.getUser("lukaB").getEmail().equals(Email.of("lukaB@gmail.com")));
	}

	@Test
	public void getUserByEmail_addUser() {
		addUsers();

		assertTrue(userDAO.getUser(Email.of("luka@gmail.com")).getUserName().equals("luka"));
		assertTrue(userDAO.getUser(Email.of("luka@gmail.com")).getPassword().equals(EncryptedPassword.of("1234#Luka")));
		assertTrue(userDAO.getUser(Email.of("luka@gmail.com")).getEmail().equals(Email.of("luka@gmail.com")));

		assertTrue(userDAO.getUser(Email.of("lukaA@gmail.com")).getUserName().equals("lukaA"));
		assertTrue(userDAO.getUser(Email.of("lukaA@gmail.com")).getPassword().equals(EncryptedPassword.of("1234#LukaA")));
		assertTrue(userDAO.getUser(Email.of("lukaA@gmail.com")).getEmail().equals(Email.of("lukaA@gmail.com")));

		assertTrue(userDAO.getUser(Email.of("lukaB@gmail.com")).getUserName().equals("lukaB"));
		assertTrue(userDAO.getUser(Email.of("lukaB@gmail.com")).getPassword().equals(EncryptedPassword.of("1234#LukaB")));
		assertTrue(userDAO.getUser(Email.of("lukaB@gmail.com")).getEmail().equals(Email.of("lukaB@gmail.com")));
	}

	@Test
	public void getAllUsers_addUser() {
		assertEquals(0, userDAO.getAllUsers().size());
		addUsers();

		assertEquals(3, userDAO.getAllUsers().size());
		List<User> allUsers = userDAO.getAllUsers();

		assertTrue(userExists("luka", allUsers));
		assertTrue(userExists("lukaA", allUsers));
		assertTrue(userExists("lukaB", allUsers));

		assertFalse(userExists("foo", allUsers));
	}

	@Test
	public void updatePassword_addUser_getUserByName_getUserByEmail() {
		addUsers();

		assertTrue(userDAO.updatePassword("luka", EncryptedPassword.of("Hamburger#0")));
		assertTrue(userDAO.updatePassword("lukaA", EncryptedPassword.of("Hamburger#1")));
		assertTrue(userDAO.updatePassword("lukaB", EncryptedPassword.of("Hamburger#2")));


		assertTrue(userDAO.getUser("luka").getPassword().equals(EncryptedPassword.of("Hamburger#0")));
		assertTrue(userDAO.getUser("lukaA").getPassword().equals(EncryptedPassword.of("Hamburger#1")));
		assertTrue(userDAO.getUser("lukaB").getPassword().equals(EncryptedPassword.of("Hamburger#2")));

		assertTrue(userDAO.getUser(Email.of("luka@gmail.com")).getPassword().equals(EncryptedPassword.of("Hamburger#0")));
		assertTrue(userDAO.getUser(Email.of("lukaA@gmail.com")).getPassword().equals(EncryptedPassword.of("Hamburger#1")));
		assertTrue(userDAO.getUser(Email.of("lukaB@gmail.com")).getPassword().equals(EncryptedPassword.of("Hamburger#2")));
	}

	@Test
	public void updateEmail_addUser_getUserByName_getUserByEmail() {
		addUsers();

		assertTrue(userDAO.updateEmail("luka", "luka@freeuni.edu.ge"));
		assertTrue(userDAO.updateEmail("lukaA", "lukaA@freeuni.edu.ge"));
		assertTrue(userDAO.updateEmail("lukaB", "lukaB@freeuni.edu.ge"));


		assertTrue(userDAO.getUser("luka").getEmail().equals(Email.of("luka@freeuni.edu.ge")));
		assertTrue(userDAO.getUser("lukaA").getEmail().equals(Email.of("lukaA@freeuni.edu.ge")));
		assertTrue(userDAO.getUser("lukaB").getEmail().equals(Email.of("lukaB@freeuni.edu.ge")));

		assertTrue(userDAO.getUser(Email.of("luka@freeuni.edu.ge")).getUserName().equals("luka"));
		assertTrue(userDAO.getUser(Email.of("lukaA@freeuni.edu.ge")).getUserName().equals("lukaA"));
		assertTrue(userDAO.getUser(Email.of("lukaB@freeuni.edu.ge")).getUserName().equals("lukaB"));
	}



	private boolean userExists(String userName, List<User> allUsers) {
		for (User user: allUsers) {
			if (user.getUserName().equals(userName)) { return true; }
		}

		return false;
	}

}
