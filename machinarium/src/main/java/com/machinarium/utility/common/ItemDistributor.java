package com.machinarium.utility.common;

import com.machinarium.dao.GameDAO;
import com.machinarium.dao.GarageDAO;
import com.machinarium.dao.ItemDAO;
import com.machinarium.model.Item.Item;
import com.machinarium.model.user.User;
import com.machinarium.utility.constants.ServletConstants;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ItemDistributor {

    private static final int MAX_N_DISTINCT_ITEMS = 5;
    private static final int MAX_N_DUPLICATE_ITEMS = 5;

    private final static Logger logger = ConfiguredLogger.getLogger("ItemDistributor");

    /**
     * Distributes a random amount of randomly chosen items from the database to all users in the specified
     * participants list.
     *
     * @param gameParticipants The list of participant usernames as a {@link List<String>}.
     * @param request The {@link HttpServletRequest} sent to start the game.
     */
    public static void distributeStartingItems(List<String> gameParticipants, HttpServletRequest request) {

        ServletContext contextListener = request.getServletContext();

        ItemDAO itemDAO = (ItemDAO) contextListener.getAttribute(ServletConstants.ATTRIBUTE_ITEM_DAO);
        GarageDAO garageDAO = (GarageDAO) contextListener.getAttribute(ServletConstants.ATTRIBUTE_GARAGE_DAO);

        List<Item> allItems = itemDAO.getAllItems();

        for(String userName : gameParticipants) {

            Map<ID, Integer> startingItems = generateStartingItems(allItems);

            logger.log(Level.INFO, "Generated list of starting items for user(" + userName + "): " + startingItems);

            for(ID itemID : startingItems.keySet()) garageDAO.addSpareItem(userName, itemID, startingItems.get(itemID));
        }
    }

    private static Map<ID, Integer> generateStartingItems(List<Item> allItems) {

        AtomicInteger nDistinctItems = new AtomicInteger();

        return allItems.stream().map(Item::getID).map(itemID -> {

            if (nDistinctItems.get() < MAX_N_DISTINCT_ITEMS && random.nextBoolean()) {

                nDistinctItems.getAndIncrement();
                return Map.entry(itemID, 1 + random.nextInt(MAX_N_DUPLICATE_ITEMS));

            } else return null;

        }).filter(Objects::nonNull).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static final Random random;

    static {
        random = new Random();
        random.setSeed(System.currentTimeMillis());
    }
}
