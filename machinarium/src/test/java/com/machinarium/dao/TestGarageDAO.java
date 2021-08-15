package com.machinarium.dao;

import com.machinarium.dao.implementation.BlockingConnectionPool;
import com.machinarium.dao.implementation.GarageDAOClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestGarageDAO {
	GarageDAO garageDAO;

	@BeforeEach
	public void init() {
		ConnectionPool connectionPool = BlockingConnectionPool.getInstance();
		garageDAO = new GarageDAOClass(connectionPool);
	}

	@Test
	public void Test_0() {
		assertTrue(1 == 1);
		assertFalse(1 == 2);
		assertEquals(1, 1);



	}


}
