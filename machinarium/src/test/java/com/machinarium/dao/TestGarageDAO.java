package com.machinarium.dao;

import com.machinarium.common.TestDBManager;
import com.machinarium.dao.implementation.BlockingConnectionPool;
import com.machinarium.dao.implementation.GarageDAOClass;
import com.machinarium.dao.implementation.UserDAOClass;
import com.machinarium.model.car.Car;
import com.machinarium.utility.common.EncryptedPassword;
import com.machinarium.utility.common.ID;
import org.junit.jupiter.api.*;

import java.util.List;

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
		ID lukaCar1ID = garageDAO.addEmptyCar("luka", "lukaCar1");
		assertTrue(garageDAO.hasCar("luka"));
		ID lukaCar2ID = garageDAO.addEmptyCar("luka", "lukaCar2");
		assertTrue(garageDAO.hasCar("luka"));

		assertFalse(garageDAO.hasCar("lukaA"));

		ID lukaBCar1ID = garageDAO.addEmptyCar("lukaB", "lukaBCar1");
		assertTrue(garageDAO.hasCar("lukaB"));
	}

	@Test
	public void addEmptyCar_getCarCount() {
		ID lukaCar1ID = garageDAO.addEmptyCar("luka", "lukaCar1");
		assertEquals(1, garageDAO.getCarCount("luka"));
		ID lukaCar2ID = garageDAO.addEmptyCar("luka", "lukaCar2");
		assertEquals(2, garageDAO.getCarCount("luka"));

		assertEquals(0, garageDAO.getCarCount("lukaA"));

		ID lukaBCar1ID = garageDAO.addEmptyCar("lukaB", "lukaBCar1");
		assertEquals(1, garageDAO.getCarCount("lukaB"));
	}

	@Test
	public void removeCar_hasCar() {
		assertFalse(garageDAO.hasCar("luka"));

		ID lukaCar1ID = garageDAO.addEmptyCar("luka", "lukaCar1");
		assertTrue(garageDAO.hasCar("luka"));

		assertTrue(garageDAO.removeCar(lukaCar1ID));
		assertFalse(garageDAO.hasCar("luka"));

		assertFalse(garageDAO.removeCar(ID.of(-1)));
	}

	@Test
	public void removeCar_getCarCount() {
		assertEquals(0, garageDAO.getCarCount("luka"));

		ID lukaCar1ID = garageDAO.addEmptyCar("luka", "lukaCar1");
		assertEquals(1, garageDAO.getCarCount("luka"));

		assertTrue(garageDAO.removeCar(lukaCar1ID));
		assertEquals(0, garageDAO.getCarCount("luka"));

		ID lukaCar11ID = garageDAO.addEmptyCar("luka", "lukaCar11");
		assertEquals(1, garageDAO.getCarCount("luka"));
		ID lukaCar12ID = garageDAO.addEmptyCar("luka", "lukaCar12");
		assertEquals(2, garageDAO.getCarCount("luka"));
		ID lukaCar13ID = garageDAO.addEmptyCar("luka", "lukaCar13");
		assertEquals(3, garageDAO.getCarCount("luka"));
	}

	@Test
	public void updateCarName_addEmptyCar() {
		ID lukaCar1ID = garageDAO.addEmptyCar("luka", "lukaCar1");
		ID lukaCar2ID = garageDAO.addEmptyCar("luka", "lukaCar2");

		assertTrue(garageDAO.updateCarName(lukaCar1ID, "lukaCar1Updated"));
		assertTrue(garageDAO.updateCarName(lukaCar2ID, "lukaCar2Updated"));
	}

	@Test
	public void getCar_addEmptyCar_getCarCount() {
		assertEquals(0, garageDAO.getCarCount("luka"));
		assertNull(garageDAO.getCar(ID.of(-1)));
		assertNull(garageDAO.getCar(ID.of(-3333333)));

		ID lukaCar1ID = garageDAO.addEmptyCar("luka", "lukaCar1");
		assertEquals(1, garageDAO.getCarCount("luka"));
		assertNotEquals(null, garageDAO.getCar(lukaCar1ID));

		ID lukaCar2ID = garageDAO.addEmptyCar("luka", "lukaCar2");
		assertEquals(2, garageDAO.getCarCount("luka"));
		assertNotEquals(null, garageDAO.getCar(lukaCar2ID));

		assertEquals(0, garageDAO.getCarCount("lukaA"));
		assertNull(garageDAO.getCar(ID.of(-5555555)));
	}

	@Test
	public void updateCarName_getCarCount() {
		assertEquals(0, garageDAO.getCarCount("luka"));

		ID lukaCar1ID = garageDAO.addEmptyCar("luka", "lukaCar1");
		assertNotNull(lukaCar1ID);

		Car lukaCar1 = garageDAO.getCar(lukaCar1ID);
		assertNotNull(lukaCar1);
		assertTrue(lukaCar1.getName().equals("lukaCar1"));

		assertTrue(garageDAO.updateCarName(lukaCar1ID, "lukaCar1Updated"));
		lukaCar1 = garageDAO.getCar(lukaCar1ID);
		assertNotNull(lukaCar1);
		assertTrue(lukaCar1.getName().equals("lukaCar1Updated"));

		assertTrue(garageDAO.updateCarName(lukaCar1ID, "lukaCar1SuperUpdated"));
		lukaCar1 = garageDAO.getCar(lukaCar1ID);
		assertNotNull(lukaCar1);
		assertTrue(lukaCar1.getName().equals("lukaCar1SuperUpdated"));

		assertTrue(garageDAO.updateCarName(lukaCar1ID, ""));
		lukaCar1 = garageDAO.getCar(lukaCar1ID);
		assertNotNull(lukaCar1);
		assertTrue(lukaCar1.getName().equals(""));


		assertFalse(garageDAO.updateCarName(ID.of(-1), "lukaCar1SuperUpdated"));
		assertFalse(garageDAO.updateCarName(ID.of(-86786768), "lukaCar1SuperUpdated"));
	}

	@Test
	public void getAllCars_addEmptyCar() {
		assertEquals(0, garageDAO.getAllCars("luka").size());

		ID lukaCar1ID = garageDAO.addEmptyCar("luka", "lukaCar1");
		assertNotNull(lukaCar1ID);
		assertEquals(1, garageDAO.getAllCars("luka").size());

		ID lukaCar2ID = garageDAO.addEmptyCar("luka", "lukaCar2");
		assertNotNull(lukaCar2ID);
		assertEquals(2, garageDAO.getAllCars("luka").size());

		ID lukaCar3ID = garageDAO.addEmptyCar("luka", "lukaCar3");
		assertNotNull(lukaCar3ID);
		assertEquals(3, garageDAO.getAllCars("luka").size());

		containsCarName("lukaCar1", garageDAO.getAllCars("luka"));
		containsCarName("lukaCar2", garageDAO.getAllCars("luka"));
		containsCarName("lukaCar3", garageDAO.getAllCars("luka"));
	}

	@Test
	public void getAllCarItems() {
		assertEquals(0, garageDAO.getAllCarItems(ID.of(-1)).size());

		ID supraID = garageDAO.addEmptyCar("luka", "supra");
		assertNotNull(supraID);
		assertEquals(1, garageDAO.getAllCars("luka").size());

		ID rotaryID = garageDAO.addEmptyCar("luka", "rotary");
		assertNotNull(rotaryID);
		assertEquals(2, garageDAO.getAllCars("luka").size());

		//**********//

		assertEquals(0, garageDAO.getAllCarItems(supraID).size());

	}











	private void addUsers() {
		assertTrue(userDAO.addUser("luka", EncryptedPassword.of("1234#Luka"), "luka@gmail.com"));
		assertTrue(userDAO.addUser("lukaA", EncryptedPassword.of("1234#LukaA"), "lukaA@gmail.com"));
		assertTrue(userDAO.addUser("lukaB", EncryptedPassword.of("1234#LukaB"), "lukaB@gmail.com"));
	}

	private boolean containsCarName(String carName, List<Car> cars) {
		for (Car car : cars) {
			if (car.getName().equals(carName)) {
				return true;
			}
		}

		return false;
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
