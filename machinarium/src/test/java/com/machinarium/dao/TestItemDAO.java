package com.machinarium.dao;

import com.machinarium.common.TestDBManager;
import com.machinarium.dao.implementation.BlockingConnectionPool;
import com.machinarium.dao.implementation.ItemDAOClass;
import com.machinarium.dao.implementation.UserDAOClass;
import com.machinarium.model.Item.part.Wheels;
import com.machinarium.utility.common.ID;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestItemDAO {
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
    public void getWheels(){
        ItemDAOClass itemDAOClass = new ItemDAOClass(connectionPool);
        Wheels wheelsSoft = new Wheels(ID.of(7), "Soft Compound", 300, 7);
        Wheels wheelsHard = new Wheels(ID.of(8), "Hard Compound", 100, 5);
        assertTrue(itemDAOClass.getItem(ID.of(7)).equals(wheelsSoft));
        assertTrue(itemDAOClass.getItem(ID.of(8)).equals(wheelsHard));
    }
}
