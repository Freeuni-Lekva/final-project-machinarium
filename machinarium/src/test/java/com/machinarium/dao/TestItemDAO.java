
package com.machinarium.dao;

import com.machinarium.common.TestDBManager;
import com.machinarium.dao.implementation.BlockingConnectionPool;
import com.machinarium.dao.implementation.ItemDAOClass;
import com.machinarium.model.Item.Item;
import com.machinarium.model.Item.connector.Connector;
import com.machinarium.model.Item.part.*;
import com.machinarium.utility.common.ID;
import org.junit.jupiter.api.*;
import org.junit.platform.commons.util.CollectionUtils;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TestItemDAO {
    private static ConnectionPool connectionPool;
    private static ItemDAO itemDAO;

    @BeforeAll
    public static void begin() {
        TestDBManager.resetDB();
    }

    @BeforeEach
    public void setup() {
        connectionPool = BlockingConnectionPool.getInstance();
        itemDAO = new ItemDAOClass(connectionPool);
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
    public void getChassisTest(){
        Chassis chassis = new Chassis(ID.of(1), "Carbon Fiber", 50,1200);
        assertTrue(itemDAO.getItem(ID.of(1)).toString().equals(chassis.toString()));
    }
    @Test
    public void getBodyTest(){
        Body lowDragBody = new Body(ID.of(2), "Low Drag", 300, 1);
        assertTrue(itemDAO.getItem(ID.of(2)).toString().equals(lowDragBody.toString()));

        Body highDownforceBody = new Body(ID.of(3), "High Downforce", 100, 3);
        assertTrue(itemDAO.getItem(ID.of(3)).toString().equals(highDownforceBody.toString()));
    }
    @Test
    public void getEngineTest(){
        Engine jzEngine = new Engine(ID.of(4), "2-JZ", 600, 800);
        assertTrue(itemDAO.getItem(ID.of(4)).toString().equals(jzEngine.toString()));

        Engine rotaryEngine = new Engine(ID.of(5), "Rotary", 300, 500);
        assertTrue(itemDAO.getItem(ID.of(5)).toString().equals(rotaryEngine.toString()));
    }
    @Test
    public void getTransmissionTest(){
        Transmission zfTransmission = new Transmission(ID.of(6), "ZF 8-Speed", 50);
        assertTrue(itemDAO.getItem(ID.of(6)).toString().equals(zfTransmission.toString()));
    }
    @Test
    public void getWheelsTest(){
        Wheels wheelsSoft = new Wheels(ID.of(7), "Soft Compound", 300, 7);
        assertTrue(itemDAO.getItem(ID.of(7)).toString().equals(wheelsSoft.toString()));

        Wheels wheelsHard = new Wheels(ID.of(8), "Hard Compound", 100, 5);
        assertTrue(itemDAO.getItem(ID.of(8)).toString().equals(wheelsHard.toString()));
    }
    @Test
    public void getBodyMountTest(){
        Connector<Chassis, Body> bodyMount = new Connector<Chassis, Body> (ID.of(9), "Body Mount",
                new Chassis(null, null, null, null),
                new Body(null, null, null, null));
        assertTrue(itemDAO.getItem(ID.of(9)).toString().equals(bodyMount.toString()));
    }
    @Test
    public void getTransmissionMountTest(){
        Connector<Chassis, Transmission> transmissionMount =
                new Connector<Chassis, Transmission> (ID.of(10), "Transmission Mount",
                new Chassis(null, null, null, null),
                new Transmission(null, null,  null));
        assertTrue(itemDAO.getItem(ID.of(10)).toString().equals(transmissionMount.toString()));
    }
    @Test
    public void getSuspensionTest(){
        Connector<Chassis, Wheels> suspension =
                new Connector<Chassis, Wheels> (ID.of(11), "Suspension",
                        new Chassis(null, null, null, null),
                        new Wheels(null, null, null, null));
        assertTrue(itemDAO.getItem(ID.of(11)).toString().equals(suspension.toString()));
    }
    @Test
    public void getEngineBoltsTest(){
        Connector<Chassis, Engine> engineBolts =
                new Connector<Chassis, Engine> (ID.of(12), "Engine Bolts",
                        new Chassis(null, null, null, null),
                        new Engine(null, null, null, null));
        assertTrue(itemDAO.getItem(ID.of(12)).toString().equals(engineBolts.toString()));
    }
    @Test
    public void getFrictionPlateTest(){
        Connector<Engine, Transmission> frictionPlace =
                new Connector<Engine, Transmission> (ID.of(13), "Friction Plate",
                        new Engine(null, null, null, null),
                        new Transmission(null, null, null));
        assertTrue(itemDAO.getItem(ID.of(13)).toString().equals(frictionPlace.toString()));
    }
    @Test
    public void getDifferentialTest(){
        Connector<Transmission, Wheels> differential =
                new Connector<Transmission, Wheels> (ID.of(14), "Differential",
                        new Transmission(null, null, null),
                        new Wheels(null, null, null, null));
        assertTrue(itemDAO.getItem(ID.of(14)).toString().equals(differential.toString()));
    }
    @Test
    public void getAllItemsTest(){
        Set<Item> allItemsDAO = new HashSet<>(itemDAO.getAllItems());
        System.out.println(allItemsDAO.size());
//        Set<Item> allItemsTest = new HashSet<>();
//
//        allItemsTest.add(new Chassis(ID.of(1), "Carbon Fiber", 50,1200));
//        allItemsTest.add(new Body(ID.of(2), "Low Drag", 300, 1));
//        allItemsTest.add(new Body(ID.of(3), "High Downforce", 100, 3));
//        allItemsTest.add(new Engine(ID.of(4), "2-JZ", 600, 800));
//        allItemsTest.add(new Engine(ID.of(5), "Rotary", 300, 500));
//        allItemsTest.add(new Transmission(ID.of(6), "ZF 8-Speed", 50));
//        allItemsTest.add(new Wheels(ID.of(7), "Soft Compound", 300, 7));
//        allItemsTest.add(new Wheels(ID.of(8), "Hard Compound", 100, 5));
//        allItemsTest.add(new Connector<Chassis, Body> (ID.of(1001), "Body Mount",
//                new Chassis(ID.of(1), "CHASSIS", null, null),
//                new Body(ID.of(2), "BODY", null, null)));
//        allItemsTest.add(new Connector<Chassis, Transmission> (ID.of(1002), "Transmission Mount",
//                        new Chassis(ID.of(1), "CHASSIS", null, null),
//                        new Transmission(ID.of(4), "TRANSMISSION", null)));
//        allItemsTest.add(new Connector<Chassis, Wheels> (ID.of(1003), "Suspension",
//                new Chassis(ID.of(1), "CHASSIS", null, null),
//                new Wheels(ID.of(5), "WHEELS", null, null)));
//        allItemsTest.add(new Connector<Chassis, Engine> (ID.of(1004), "Engine Bolts",
//                new Chassis(ID.of(1), "CHASSIS", null, null),
//                new Engine(ID.of(3), "ENGINE", null, null)));
//        allItemsTest.add(new Connector<Engine, Transmission> (ID.of(1005), "Friction Plate",
//                new Engine(ID.of(3), "ENGINE", null, null),
//                new Transmission(ID.of(4), "TRANSMISSION", null)));
//        allItemsTest.add(new Connector<Transmission, Wheels> (ID.of(1006), "Differential",
//                new Transmission(ID.of(4), "TRANSMISSION", null),
//                new Wheels(ID.of(5), "WHEELS", null, null)));
        System.out.println(allItemsDAO);
//        for (i:
//             allItemsDAO) {
//            System.out.println(i);
//        }
//        assertTrue(allItemsDAO.size() == allItemsTest.size());

    }
}