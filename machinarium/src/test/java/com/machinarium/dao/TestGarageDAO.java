package com.machinarium.dao;

import com.machinarium.common.TestDBManager;
import com.machinarium.dao.implementation.BlockingConnectionPool;
import com.machinarium.dao.implementation.GarageDAOClass;
import com.machinarium.dao.implementation.UserDAOClass;
import com.machinarium.utility.common.EncryptedPassword;
import com.machinarium.utility.common.ID;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


public class TestGarageDAO {
	private static ConnectionPool connectionPool;
	private static UserDAO userDAO;
	private static GarageDAO garageDAO;

	@BeforeAll
	public static void begin() {
		TestDBManager.resetDB();
	}

	@BeforeEach
	public void setup() {
		connectionPool = BlockingConnectionPool.getInstance();
		userDAO = new UserDAOClass(connectionPool);
		garageDAO = new GarageDAOClass(connectionPool);

		addUsers();
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
	public void hasCar() {
		assertFalse(garageDAO.hasCar("luka"));
		assertFalse(garageDAO.hasCar("lukaA"));
		assertFalse(garageDAO.hasCar("lukaB"));

		assertFalse(garageDAO.hasCar("bla"));
	}

	@Test
	public void getCarCount() {
		assertEquals(0, garageDAO.getCarCount("luka"));
		assertEquals(0, garageDAO.getCarCount("lukaA"));
		assertEquals(0, garageDAO.getCarCount("lukaB"));

		assertEquals(0, garageDAO.getCarCount("bla"));
	}

	@Test
	public void addEmptyCar_hasCar() {
		ID lukaCar1 = garageDAO.addEmptyCar("luka", "lukaCar1");
		assertTrue(garageDAO.hasCar("luka"));
		ID lukaCar2 = garageDAO.addEmptyCar("luka", "lukaCar2");
		assertTrue(garageDAO.hasCar("luka"));

		assertFalse(garageDAO.hasCar("lukaA"));

		ID lukaBCar1 = garageDAO.addEmptyCar("lukaB", "lukaBCar1");
		assertTrue(garageDAO.hasCar("lukaB"));
	}

	@Test
	public void addEmptyCar_getCarCount() {
		ID lukaCar1 = garageDAO.addEmptyCar("luka", "lukaCar1");
		assertEquals(1, garageDAO.getCarCount("luka"));
		ID lukaCar2 = garageDAO.addEmptyCar("luka", "lukaCar2");
		assertEquals(2, garageDAO.getCarCount("luka"));

		assertEquals(0, garageDAO.getCarCount("lukaA"));

		ID lukaBCar1 = garageDAO.addEmptyCar("lukaB", "lukaBCar1");
		assertEquals(1, garageDAO.getCarCount("lukaB"));
	}

	@Test
	public void removeCar_hasCar() {
		assertFalse(garageDAO.hasCar("luka"));

		ID lukaCar1 = garageDAO.addEmptyCar("luka", "lukaCar1");
		assertTrue(garageDAO.hasCar("luka"));

		assertTrue(garageDAO.removeCar(lukaCar1));
		assertFalse(garageDAO.hasCar("luka"));
	}

	@Test
	public void removeCar_getCarCount() {
		assertEquals(0, garageDAO.getCarCount("luka"));

		ID lukaCar1 = garageDAO.addEmptyCar("luka", "lukaCar1");
		assertEquals(1, garageDAO.getCarCount("luka"));

		assertTrue(garageDAO.removeCar(lukaCar1));
		assertEquals(0, garageDAO.getCarCount("luka"));

		ID lukaCar11 = garageDAO.addEmptyCar("luka", "lukaCar11");
		assertEquals(1, garageDAO.getCarCount("luka"));
		ID lukaCar12 = garageDAO.addEmptyCar("luka", "lukaCar12");
		assertEquals(2, garageDAO.getCarCount("luka"));
		ID lukaCar13 = garageDAO.addEmptyCar("luka", "lukaCar13");
		assertEquals(3, garageDAO.getCarCount("luka"));
	}

	@Test
	public void updateCarName_addEmptyCar() {
		ID lukaCar1 = garageDAO.addEmptyCar("luka", "lukaCar1");
		ID lukaCar2 = garageDAO.addEmptyCar("luka", "lukaCar2");

		assertTrue(garageDAO.updateCarName(lukaCar1, "lukaCar1Updated"));
		assertTrue(garageDAO.updateCarName(lukaCar2, "lukaCar2Updated"));
	}

	@Test
	public void getCar_addEmptyCar() {
		System.out.println("************" + garageDAO.getCar(ID.of(33333)));
//		assertEquals(null, garageDAO.getCar(ID.of(33333)));

		ID lukaCar1 = garageDAO.addEmptyCar("luka", "lukaCar1");
		ID lukaCar2 = garageDAO.addEmptyCar("luka", "lukaCar2");
	}










	private void addUsers() {
		assertTrue(userDAO.addUser("luka", EncryptedPassword.of("1234#Luka"), "luka@gmail.com"));
		assertTrue(userDAO.addUser("lukaA", EncryptedPassword.of("1234#LukaA"), "lukaA@gmail.com"));
		assertTrue(userDAO.addUser("lukaB", EncryptedPassword.of("1234#LukaB"), "lukaB@gmail.com"));
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
