package com.machinarium.dao;

import com.machinarium.dao.implementation.BlockingConnectionPool;
import com.machinarium.dao.implementation.GarageDAOClass;
import com.machinarium.utility.common.ID;
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

	}

	@Test
	public void Test_1() {


		assertFalse(garageDAO.hasCar("luka"));
		assertEquals(0, garageDAO.getCarCount("luka"));

		ID carAiD = garageDAO.addEmptyCar("luka", "carA");
		ID carBiD = garageDAO.addEmptyCar("luka", "carB");
		ID carCiD = garageDAO.addEmptyCar("luka", "carC");
		assertEquals(3, garageDAO.getCarCount("luka"));
		assertEquals(3, garageDAO.getAllCars("luka").size());
		System.out.println("******" + garageDAO.getAllCars("luka"));

		assertTrue(garageDAO.removeCar(carBiD));
		assertEquals(2, garageDAO.getCarCount("luka"));
		assertEquals(2, garageDAO.getAllCars("luka").size());

		assertTrue(garageDAO.removeCar(carCiD));
		assertEquals(1, garageDAO.getCarCount("luka"));
		assertEquals(1, garageDAO.getAllCars("luka").size());

		assertTrue(garageDAO.removeCar(carAiD));
		assertEquals(0, garageDAO.getCarCount("luka"));
		assertEquals(0, garageDAO.getAllCars("luka").size());









	}


}
