package com.machinarium.dao;

import com.machinarium.common.TestDBManager;
import com.machinarium.dao.implementation.BlockingConnectionPool;
import com.machinarium.dao.implementation.GarageDAOClass;
import com.machinarium.dao.implementation.UserDAOClass;
import com.machinarium.model.Item.Item;
import com.machinarium.model.car.Car;
import com.machinarium.utility.common.EncryptedPassword;
import com.machinarium.utility.common.ID;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
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


	//******************************* Car ******************************************//
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
		assertEquals(0, garageDAO.getAllCarItems(rotaryID).size());
	}

	@Test
	public void addItemPartToCar() {
		ID supraID = garageDAO.addEmptyCar("luka", "supra");
		assertNotNull(supraID);
		assertEquals(1, garageDAO.getAllCars("luka").size());

		//**********//
		assertEquals(0, garageDAO.getAllCarItems(supraID).size());

		assertTrue(garageDAO.addItemToCar(supraID, ID.of(1))); // Carbon Fiber
		assertEquals(1, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(2))); // Low Drag
		assertEquals(2, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(3))); // High Downforce
		assertEquals(3, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(4))); // 2-JZ
		assertEquals(4, garageDAO.getAllCarItems(supraID).size());

		assertTrue(garageDAO.addItemToCar(supraID, ID.of(5))); // Rotary
		assertEquals(5, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(6))); // ZF 8-Speed
		assertEquals(6, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(7))); // Soft Compound
		assertEquals(7, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(8))); // Hard Compound
		assertEquals(8, garageDAO.getAllCarItems(supraID).size());

		//**********//
		List<Item> allCarItems = garageDAO.getAllCarItems(supraID);
		List<String> allCarItemNames = getNames(allCarItems);
		assertTrue(allCarItemNames.containsAll(Arrays.asList("Carbon Fiber", "Low Drag",
				"High Downforce", "2-JZ", "Rotary", "ZF 8-Speed", "Soft Compound", "Hard Compound")));

		System.out.println(allCarItems);
	}

	@Test
	public void addItemConnectorToCar() {
		ID supraID = garageDAO.addEmptyCar("luka", "supra");
		assertNotNull(supraID);
		assertEquals(1, garageDAO.getAllCars("luka").size());

		//**********//
		assertEquals(0, garageDAO.getAllCarItems(supraID).size());

		assertTrue(garageDAO.addItemToCar(supraID, ID.of(9))); // Body Mount
		assertEquals(1, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(10))); // Transmission Mount
		assertEquals(2, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(11))); // Suspension
		assertEquals(3, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(12))); // Engine Bolts
		assertEquals(4, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(13))); // Friction Plate
		assertEquals(5, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(14))); // Differential
		assertEquals(6, garageDAO.getAllCarItems(supraID).size());

		//**********//
		List<Item> allCarItems = garageDAO.getAllCarItems(supraID);
		List<String> allCarItemNames = getNames(allCarItems);
		assertTrue(allCarItemNames.containsAll(Arrays.asList("Body Mount", "Transmission Mount",
				"Suspension", "Engine Bolts", "Friction Plate", "Differential")));

		System.out.println(allCarItems);
	}

	@Test
	public void addItemPartConnectorToCar() {
		ID supraID = garageDAO.addEmptyCar("luka", "supra");
		assertNotNull(supraID);
		assertEquals(1, garageDAO.getAllCars("luka").size());

		//**********//
		assertEquals(0, garageDAO.getAllCarItems(supraID).size());

		assertTrue(garageDAO.addItemToCar(supraID, ID.of(1))); // Carbon Fiber
		assertEquals(1, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(2))); // Low Drag
		assertEquals(2, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(3))); // High Downforce
		assertEquals(3, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(4))); // 2-JZ
		assertEquals(4, garageDAO.getAllCarItems(supraID).size());

		assertTrue(garageDAO.addItemToCar(supraID, ID.of(9))); // Body Mount
		assertEquals(5, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(10))); // Transmission Mount
		assertEquals(6, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(11))); // Suspension
		assertEquals(7, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(12))); // Engine Bolts
		assertEquals(8, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(13))); // Friction Plate
		assertEquals(9, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(14))); // Differential
		assertEquals(10, garageDAO.getAllCarItems(supraID).size());

		assertTrue(garageDAO.addItemToCar(supraID, ID.of(5))); // Rotary
		assertEquals(11, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(6))); // ZF 8-Speed
		assertEquals(12, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(7))); // Soft Compound
		assertEquals(13, garageDAO.getAllCarItems(supraID).size());
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(8))); // Hard Compound
		assertEquals(14, garageDAO.getAllCarItems(supraID).size());

		//**********//
		List<Item> allCarItems = garageDAO.getAllCarItems(supraID);
		List<String> allCarItemNames = getNames(allCarItems);
		assertTrue(allCarItemNames.containsAll(Arrays.asList("Carbon Fiber", "Low Drag",
				"High Downforce", "2-JZ", "Rotary", "ZF 8-Speed", "Soft Compound", "Hard Compound",
				"Body Mount", "Transmission Mount", "Suspension", "Engine Bolts",
				"Friction Plate", "Differential")));

		System.out.println(allCarItems);
	}

	//------------------------------------------//

	@Test
	public void hasItemPart() {
		ID supraID = garageDAO.addEmptyCar("luka", "supra");
		assertNotNull(supraID);
		assertEquals(1, garageDAO.getAllCars("luka").size());

		assertFalse(garageDAO.carHasItem(supraID, ID.of(1))); // Carbon Fiber

		assertTrue(garageDAO.addItemToCar(supraID, ID.of(1))); // Carbon Fiber
		assertTrue(garageDAO.carHasItem(supraID, ID.of(1)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(2))); // Low Drag
		assertTrue(garageDAO.carHasItem(supraID, ID.of(2)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(3))); // High Downforce
		assertTrue(garageDAO.carHasItem(supraID, ID.of(3)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(4))); // 2-JZ
		assertTrue(garageDAO.carHasItem(supraID, ID.of(4)));

		assertTrue(garageDAO.addItemToCar(supraID, ID.of(5))); // Rotary
		assertTrue(garageDAO.carHasItem(supraID, ID.of(5)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(6))); // ZF 8-Speed
		assertTrue(garageDAO.carHasItem(supraID, ID.of(6)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(7))); // Soft Compound
		assertTrue(garageDAO.carHasItem(supraID, ID.of(7)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(8))); // Hard Compound
		assertTrue(garageDAO.carHasItem(supraID, ID.of(8)));
	}

	@Test
	public void hasItemConnector() {
		ID supraID = garageDAO.addEmptyCar("luka", "supra");
		assertNotNull(supraID);
		assertEquals(1, garageDAO.getAllCars("luka").size());

		assertEquals(0, garageDAO.getAllCarItems(supraID).size());

		assertTrue(garageDAO.addItemToCar(supraID, ID.of(9))); // Body Mount
		assertTrue(garageDAO.carHasItem(supraID, ID.of(9)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(10))); // Transmission Mount
		assertTrue(garageDAO.carHasItem(supraID, ID.of(10)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(11))); // Suspension
		assertTrue(garageDAO.carHasItem(supraID, ID.of(11)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(12))); // Engine Bolts
		assertTrue(garageDAO.carHasItem(supraID, ID.of(12)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(13))); // Friction Plate
		assertTrue(garageDAO.carHasItem(supraID, ID.of(13)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(14))); // Differential
		assertTrue(garageDAO.carHasItem(supraID, ID.of(14)));
	}

	@Test
	public void hasItemPartConnector() {
		ID supraID = garageDAO.addEmptyCar("luka", "supra");
		assertNotNull(supraID);
		assertEquals(1, garageDAO.getAllCars("luka").size());

		assertFalse(garageDAO.carHasItem(supraID, ID.of(1))); // Carbon Fiber

		assertTrue(garageDAO.addItemToCar(supraID, ID.of(1))); // Carbon Fiber
		assertTrue(garageDAO.carHasItem(supraID, ID.of(1)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(2))); // Low Drag
		assertTrue(garageDAO.carHasItem(supraID, ID.of(2)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(3))); // High Downforce
		assertTrue(garageDAO.carHasItem(supraID, ID.of(3)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(4))); // 2-JZ
		assertTrue(garageDAO.carHasItem(supraID, ID.of(4)));

		assertTrue(garageDAO.addItemToCar(supraID, ID.of(9))); // Body Mount
		assertTrue(garageDAO.carHasItem(supraID, ID.of(9)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(10))); // Transmission Mount
		assertTrue(garageDAO.carHasItem(supraID, ID.of(10)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(11))); // Suspension
		assertTrue(garageDAO.carHasItem(supraID, ID.of(11)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(12))); // Engine Bolts
		assertTrue(garageDAO.carHasItem(supraID, ID.of(12)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(13))); // Friction Plate
		assertTrue(garageDAO.carHasItem(supraID, ID.of(13)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(14))); // Differential
		assertTrue(garageDAO.carHasItem(supraID, ID.of(14)));

		assertTrue(garageDAO.addItemToCar(supraID, ID.of(5))); // Rotary
		assertTrue(garageDAO.carHasItem(supraID, ID.of(5)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(6))); // ZF 8-Speed
		assertTrue(garageDAO.carHasItem(supraID, ID.of(6)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(7))); // Soft Compound
		assertTrue(garageDAO.carHasItem(supraID, ID.of(7)));
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(8))); // Hard Compound
		assertTrue(garageDAO.carHasItem(supraID, ID.of(8)));
	}

	@Test
	public void removeItemPartFromCar() {
		ID supraID = garageDAO.addEmptyCar("luka", "supra");
		assertNotNull(supraID);
		assertEquals(1, garageDAO.getAllCars("luka").size());

		assertFalse(garageDAO.carHasItem(supraID, ID.of(1))); // Carbon Fiber

		assertTrue(garageDAO.addItemToCar(supraID, ID.of(1))); // Carbon Fiber
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(2))); // Low Drag
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(3))); // High Downforce
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(4))); // 2-JZ
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(5))); // Rotary
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(6))); // ZF 8-Speed
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(7))); // Soft Compound
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(8))); // Hard Compound

		assertTrue(garageDAO.removeItemFromCar(supraID, ID.of(1)));
		assertFalse(garageDAO.removeItemFromCar(supraID, ID.of(1)));
		assertTrue(garageDAO.removeItemFromCar(supraID, ID.of(2)));
		assertTrue(garageDAO.removeItemFromCar(supraID, ID.of(4)));
		assertTrue(garageDAO.removeItemFromCar(supraID, ID.of(5)));
		assertTrue(garageDAO.removeItemFromCar(supraID, ID.of(8)));


		assertFalse(garageDAO.carHasItem(supraID, ID.of(1)));
		assertFalse(garageDAO.carHasItem(supraID, ID.of(2)));
		assertTrue(garageDAO.carHasItem(supraID, ID.of(3)));
		assertFalse(garageDAO.carHasItem(supraID, ID.of(4)));
		assertFalse(garageDAO.carHasItem(supraID, ID.of(5)));
		assertTrue(garageDAO.carHasItem(supraID, ID.of(6)));
		assertTrue(garageDAO.carHasItem(supraID, ID.of(7)));
		assertFalse(garageDAO.carHasItem(supraID, ID.of(8)));
	}

	@Test
	public void removeItemConnectorFromCar() {
		ID supraID = garageDAO.addEmptyCar("luka", "supra");
		assertNotNull(supraID);
		assertEquals(1, garageDAO.getAllCars("luka").size());

		assertFalse(garageDAO.carHasItem(supraID, ID.of(1))); // Carbon Fiber

		assertTrue(garageDAO.addItemToCar(supraID, ID.of(9))); // Body Mount
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(10))); // Transmission Mount
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(11))); // Suspension
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(12))); // Engine Bolts
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(13))); // Friction Plate
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(14))); // Differential

		assertTrue(garageDAO.removeItemFromCar(supraID, ID.of(10)));
		assertFalse(garageDAO.removeItemFromCar(supraID, ID.of(10)));
		assertTrue(garageDAO.removeItemFromCar(supraID, ID.of(12)));
		assertTrue(garageDAO.removeItemFromCar(supraID, ID.of(14)));


		assertTrue(garageDAO.carHasItem(supraID, ID.of(9)));
		assertFalse(garageDAO.carHasItem(supraID, ID.of(10)));
		assertTrue(garageDAO.carHasItem(supraID, ID.of(11)));
		assertFalse(garageDAO.carHasItem(supraID, ID.of(12)));
		assertTrue(garageDAO.carHasItem(supraID, ID.of(13)));
		assertFalse(garageDAO.carHasItem(supraID, ID.of(14)));
	}

	@Test
	public void removeItemPartConnectorFromCar() {
		ID supraID = garageDAO.addEmptyCar("luka", "supra");
		assertNotNull(supraID);
		assertEquals(1, garageDAO.getAllCars("luka").size());

		assertFalse(garageDAO.carHasItem(supraID, ID.of(1))); // Carbon Fiber

		assertTrue(garageDAO.addItemToCar(supraID, ID.of(1))); // Carbon Fiber
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(2))); // Low Drag
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(3))); // High Downforce
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(4))); // 2-JZ
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(9))); // Body Mount
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(10))); // Transmission Mount
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(11))); // Suspension
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(12))); // Engine Bolts
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(13))); // Friction Plate
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(14))); // Differential
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(5))); // Rotary
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(6))); // ZF 8-Speed
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(7))); // Soft Compound
		assertTrue(garageDAO.addItemToCar(supraID, ID.of(8))); // Hard Compound

		assertTrue(garageDAO.removeItemFromCar(supraID, ID.of(1)));
		assertFalse(garageDAO.removeItemFromCar(supraID, ID.of(1)));
		assertTrue(garageDAO.removeItemFromCar(supraID, ID.of(2)));
		assertTrue(garageDAO.removeItemFromCar(supraID, ID.of(10)));
		assertFalse(garageDAO.removeItemFromCar(supraID, ID.of(10)));
		assertTrue(garageDAO.removeItemFromCar(supraID, ID.of(12)));
		assertTrue(garageDAO.removeItemFromCar(supraID, ID.of(14)));
		assertTrue(garageDAO.removeItemFromCar(supraID, ID.of(4)));
		assertTrue(garageDAO.removeItemFromCar(supraID, ID.of(5)));
		assertTrue(garageDAO.removeItemFromCar(supraID, ID.of(8)));


		assertFalse(garageDAO.carHasItem(supraID, ID.of(1)));
		assertFalse(garageDAO.carHasItem(supraID, ID.of(2)));
		assertTrue(garageDAO.carHasItem(supraID, ID.of(3)));
		assertFalse(garageDAO.carHasItem(supraID, ID.of(4)));
		assertFalse(garageDAO.carHasItem(supraID, ID.of(5)));
		assertTrue(garageDAO.carHasItem(supraID, ID.of(6)));
		assertTrue(garageDAO.carHasItem(supraID, ID.of(7)));
		assertFalse(garageDAO.carHasItem(supraID, ID.of(8)));

		assertTrue(garageDAO.carHasItem(supraID, ID.of(9)));
		assertFalse(garageDAO.carHasItem(supraID, ID.of(10)));
		assertTrue(garageDAO.carHasItem(supraID, ID.of(11)));
		assertFalse(garageDAO.carHasItem(supraID, ID.of(12)));
		assertTrue(garageDAO.carHasItem(supraID, ID.of(13)));
		assertFalse(garageDAO.carHasItem(supraID, ID.of(14)));
	}


	//**************************************** SpareItem **********************************//

	@Test
	public void hasSpareItem() {
		assertFalse(garageDAO.hasSpareItem("luka"));
		assertFalse(garageDAO.hasSpareItem("lukaA"));
		assertFalse(garageDAO.hasSpareItem("lukaB"));

		assertFalse(garageDAO.hasSpareItem("bla"));
	}

	@Test
	public void getSpareItemCount() {
		assertEquals(0, garageDAO.getSpareItemCount("luka"));
		assertEquals(0, garageDAO.getSpareItemCount("lukaA"));
		assertEquals(0, garageDAO.getSpareItemCount("lukaB"));

		assertEquals(0, garageDAO.getSpareItemCount("bla"));
	}

	@Test
	public void addSpareItem() {
		assertEquals(0, garageDAO.getSpareItemCount("luka"));
		garageDAO.addSpareItem("luka", ID.of(1), 1);
		assertEquals(1, garageDAO.getSpareItemCount("luka"));
	}















	private void addUsers() {
		userDAO.addUser("luka", EncryptedPassword.of("1234#Luka"), "luka@gmail.com");
		userDAO.addUser("lukaA", EncryptedPassword.of("1234#LukaA"), "lukaA@gmail.com");
		userDAO.addUser("lukaB", EncryptedPassword.of("1234#LukaB"), "lukaB@gmail.com");
	}

	private boolean containsCarName(String carName, List<Car> cars) {
		for (Car car : cars) {
			if (car.getName().equals(carName)) {
				return true;
			}
		}

		return false;
	}

	private List<String> getNames(List<Item> items) {
		List<String> names = new ArrayList<>();

		for (Item item : items) {
			String name = item.getName();
			names.add(name);
		}

		return names;
	}

}
