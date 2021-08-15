package com.machinarium.dao;

import com.machinarium.dao.implementation.BlockingConnectionPool;
import com.machinarium.dao.implementation.UserDAOClass;
import com.machinarium.utility.common.EncryptedPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class TestUserDAO {
	UserDAO userDAO;

	@BeforeEach
	public void init() {
		ConnectionPool connectionPool = BlockingConnectionPool.getInstance();
		userDAO = new UserDAOClass(connectionPool);
	}

	@Test
	public void Test_0() {
		assertEquals(null, userDAO.getUser("bla"));
//		assertNotEquals(null, userDAO.getUser("luka"));
		userDAO.addUser("lukaM", EncryptedPassword.of("Luka#1"), "lgela18@freeuni.edu.ge");

	}




}
