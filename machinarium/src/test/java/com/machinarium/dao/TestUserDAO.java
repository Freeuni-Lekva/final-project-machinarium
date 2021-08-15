package com.machinarium.dao;

import com.machinarium.dao.implementation.BlockingConnectionPool;
import com.machinarium.dao.implementation.UserDAOClass;
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
		assertTrue(1 == 1);
		assertFalse(1 == 2);
		assertEquals(true, true);



		Map k = new HashMap<String, Integer>();
	}




}
