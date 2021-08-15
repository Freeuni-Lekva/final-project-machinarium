package com.machinarium.dao.implementation;

import com.machinarium.dao.ConnectionPool;
import com.machinarium.dao.OrderDAO;
import com.machinarium.model.Item.Item;
import com.machinarium.model.history.Order;
import com.machinarium.utility.common.ID;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAOClass implements OrderDAO {
    private final String USERS_TABLE = "users";
    private final String USER_ORDERS_VIEW = "see_user_orders";
    private final String ORDERS_TABLE = "orders";
    private final String ORDER_ITEM_TABLE = "order_item";
    private final String USER_ORDER_TABLE = "user_order";
    private final String ORDER_ACTIVE = "active";
    private final String SOURCE_STR = "source";
    private final String DESTINATION_STR = "destination";
    private final ConnectionPool connectionPool;
    public OrderDAOClass (ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }
    private ID getUserID(String userName, Connection con){
        ID id = null;
        String getUserIDQuery = "SELECT id FROM " + USERS_TABLE + "\n"
                + "WHERE user_name = '" + userName + "';";
        try {
            Statement getUserIDStat = con.createStatement();
            ResultSet res = getUserIDStat.executeQuery(getUserIDQuery);
            if(res.next()){
                id = new ID(res.getInt("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }
    @Override
    public Order getOrder(String userName, ID orderID) {
        Connection con = connectionPool.acquireConnection();
        String getOrderQuery = "SELECT * FROM " + USER_ORDERS_VIEW
                        + "WHERE user_name = '" + userName + "' AND order_id = " + orderID.getID() + ";";
        Map<Item, Integer> userGives = new HashMap<>();
        Map<Item, Integer> userTakes = new HashMap<>();
        String orderDate = null;
        String orderStatus = null;
        try {
            Statement getOrderStat = con.createStatement();
            ResultSet res = getOrderStat.executeQuery(getOrderQuery);
            while (res.next()){
                if(orderDate == null){
                    orderDate = res.getDate("order_date").toString();
                }
                if(orderStatus == null){
                    orderStatus = res.getString("order_status");
                }
                Item item = new Item(new ID(res.getInt("item_id")), res.getString("item_name"));
                int itemCount = res.getInt("item_count");
                String givesTakes = res.getString("source_destination");
                if(givesTakes.equals(SOURCE_STR)){
                    userGives.put(item, itemCount);
                }else if (givesTakes.equals(DESTINATION_STR)){
                    userTakes.put(item, itemCount);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Order order = new Order(userName, orderID, orderStatus, orderDate, userGives, userTakes);
        connectionPool.releaseConnection(con);
        return order;
    }

    @Override
    public List<Order> getAllOrders(String userName) {
        Connection con = connectionPool.acquireConnection();
        String getAllOrdersIDQuery = "SELECT DISTINCT (order_id) FROM "
                            + USER_ORDERS_VIEW + "WHERE user_name = '" + userName + "';";
        List<Order> allOrders = new ArrayList<>();
        try {
            Statement getAllOrdersStat = con.createStatement();
            ResultSet res = getAllOrdersStat.executeQuery(getAllOrdersIDQuery);
            while (res.next()){
                Order order = getOrder(userName, new ID(res.getInt("order_id")));
                allOrders.add(order);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return allOrders;
    }

    @Override
    public ID addOrder(String userName, Map<Item, Integer> userGives, Map<Item, Integer> userTakes) {
        Connection con = connectionPool.acquireConnection();
        String addOrderInTableQuery = "INSERT INTO " + ORDERS_TABLE + "(order_status)\n"
                                    + "VALUES ('" + ORDER_ACTIVE + "');";
        ID orderID = null;
        try {
            Statement addOrderInTableStat = con.createStatement();
            if(addOrderInTableStat.executeUpdate(addOrderInTableQuery, Statement.RETURN_GENERATED_KEYS) > 0){
                ResultSet generatedID = addOrderInTableStat.getGeneratedKeys();
                if(generatedID.next()){
                    orderID = new ID(generatedID.getInt(1));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ID userID = getUserID(userName, con);
        String addUserOrderQuery = "INSERT INTO " + USER_ORDER_TABLE + "(user_id, order_id)\n"
                                    + "VALUES (" + userID.getID() + ", " + orderID.getID() + ");";
        try {
            Statement addUserOrderStat = con.createStatement();
            addUserOrderStat.executeUpdate(addUserOrderQuery);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String addItemsInOrderQuery = "INSERT INTO " + ORDER_ITEM_TABLE +
                                        "(order_id, item_id, item_amount, source_destination)\n"
                                        +"VALUES (%s, %s, %s, '%s');";
        for (Item i: userGives.keySet()
             ) {
            String addItemsInOrderSourceQuery = String.format(addItemsInOrderQuery,
                                                                orderID.getID(),
                                                                i.getID().getID(),
                                                                userGives.get(i),
                                                                SOURCE_STR);
            try {
                Statement addItemsInOrderSourceStat = con.createStatement();
                addItemsInOrderSourceStat.executeUpdate(addItemsInOrderSourceQuery)
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        for (Item i: userTakes.keySet()
        ) {
            String addItemsInOrderDestinationeQuery = String.format(addItemsInOrderQuery,
                                                                    orderID.getID(),
                                                                    i.getID().getID(),
                                                                    userTakes.get(i),
                                                                    DESTINATION_STR);
            try {
                Statement addItemsInOrderSourceStat = con.createStatement();
                addItemsInOrderSourceStat.executeUpdate(addItemsInOrderDestinationeQuery);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        connectionPool.releaseConnection(con);
        return orderID;
    }

    @Override
    public boolean removeOrder(String userName, ID orderID) {
        Connection con = connectionPool.acquireConnection();
        String removeOrderQuery = "DELETE FROM " + ORDERS_TABLE + "\n"
                                + "WHERE order_id = " + orderID.getID() + ";";
        boolean removeOrderBoolean = false;
        try {
            Statement removeOrderStat = con.createStatement();
            if (removeOrderStat.executeUpdate(removeOrderQuery) > 0)
                removeOrderBoolean = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return removeOrderBoolean;
    }
}
