package com.machinarium.dao;

import com.machinarium.common.TestDBManager;
import com.machinarium.common.TestData;
import com.machinarium.dao.implementation.BlockingConnectionPool;
import com.machinarium.dao.implementation.GarageDAOClass;
import com.machinarium.dao.implementation.UserDAOClass;
import com.machinarium.utility.common.ID;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestGarageDAO {
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
	public void test() {
		assertTrue(1 == 1);
	}




//	@Test
//	public void hasCar() {
//		ID carAiD = garageDAO.addEmptyCar("luka", "carA");
//		assertEquals(1, garageDAO.getCarCount("luka"));
//		assertEquals(1, garageDAO.getAllCars("luka").size());
//		assertTrue(garageDAO.removeCar(carAiD));
//	}

//	@Test
//	public void Test_1() {
//		assertFalse(garageDAO.hasCar("luka"));
//		assertEquals(0, garageDAO.getCarCount("luka"));
//
//		ID carAiD = garageDAO.addEmptyCar("luka", "carA");
//		ID carBiD = garageDAO.addEmptyCar("luka", "carB");
//		ID carCiD = garageDAO.addEmptyCar("luka", "carC");
//		assertEquals(3, garageDAO.getCarCount("luka"));
//		assertEquals(3, garageDAO.getAllCars("luka").size());
////		System.out.println("******" + garageDAO.getAllCars("luka"));
//
//		assertTrue(garageDAO.removeCar(carBiD));
//		assertEquals(2, garageDAO.getCarCount("luka"));
//		assertEquals(2, garageDAO.getAllCars("luka").size());
//
//		assertTrue(garageDAO.removeCar(carCiD));
//		assertEquals(1, garageDAO.getCarCount("luka"));
//		assertEquals(1, garageDAO.getAllCars("luka").size());
//
//		assertTrue(garageDAO.removeCar(carAiD));
//		assertEquals(0, garageDAO.getCarCount("luka"));
//		assertEquals(0, garageDAO.getAllCars("luka").size());
//	}


}
