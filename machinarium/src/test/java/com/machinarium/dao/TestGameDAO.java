package com.machinarium.dao;

import com.machinarium.common.TestDBManager;
import com.machinarium.dao.implementation.BlockingConnectionPool;
import com.machinarium.dao.implementation.GameDAOClass;
import com.machinarium.dao.implementation.UserDAOClass;
import com.machinarium.utility.common.EncryptedPassword;
import com.machinarium.utility.common.ID;
import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TestGameDAO {
    private static ConnectionPool connectionPool;
    private static GameDAO gameDAO;
    private static UserDAO userDAO;

    @BeforeAll
    public static void begin() {
        TestDBManager.resetDB();
    }

    @BeforeEach
    public void setup() {
        connectionPool = BlockingConnectionPool.getInstance();
        gameDAO = new GameDAOClass(connectionPool);
        userDAO = new UserDAOClass(connectionPool);
        addUsers();
    }

    private void addUsers() {
        userDAO.addUser("user", EncryptedPassword.of("1234#user"), "user@gmail.com");
        userDAO.addUser("userA", EncryptedPassword.of("1234#userA"), "userA@gmail.com");
        userDAO.addUser("userB", EncryptedPassword.of("1234#userB"), "userB@gmail.com");
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
    void getGameHost() {
        ID id = gameDAO.addGame("user");
        assertEquals("user", gameDAO.getGameHost(id));
    }

    @Test
    void getGameUsers() {
        ID id = gameDAO.addGame("user");
        gameDAO.addUser(id, "userA");
        assertEquals(2, gameDAO.getGameUsers(id).size());
        assertEquals(Arrays.asList("user", "userA"), gameDAO.getGameUsers(id));
    }

    @Test
    void getActiveGame() {
        ID id = gameDAO.addGame("user");
        assertEquals(gameDAO.getActiveGame("user").getID(), id.getID());
        gameDAO.addUser(id, "userA");
        assertEquals(gameDAO.getActiveGame("userA").getID(), id.getID());
    }

    @Test
    void getAllActiveGames() {
        gameDAO.addGame("user");
        gameDAO.addGame("userA");
        assertEquals(gameDAO.getAllActiveGames().size(), 2);
        gameDAO.addGame("userB");
        assertEquals(gameDAO.getAllActiveGames().size(), 3);
    }

    // fails when status is changed to "active"
    @Test
    void getGameStage() {
        ID id = gameDAO.addGame("user");
        assertEquals(gameDAO.getGameStage(id), GameDAO.GameStage.IN_LOBBY);
        gameDAO.updateGameStage(id, GameDAO.GameStage.ACTIVE);
        assertEquals((gameDAO.getGameStage(id)), GameDAO.GameStage.ACTIVE);
    }

}
