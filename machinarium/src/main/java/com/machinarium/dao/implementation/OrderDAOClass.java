package com.machinarium.dao.implementation;

import com.machinarium.dao.ConnectionPool;
import com.machinarium.dao.OrderDAO;
import com.machinarium.model.Item.Item;
<<<<<<< HEAD
import com.machinarium.model.user.Order;
=======
import com.machinarium.model.history.Order;
>>>>>>> დამატება #47: დაამატეთ OrderDAO ინტერფეისის იმპლემენტაცია (#55)
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
<<<<<<< HEAD

=======
>>>>>>> დამატება #47: დაამატეთ OrderDAO ინტერფეისის იმპლემენტაცია (#55)
    private final String USERS_TABLE = "users";
    private final String USER_ORDERS_VIEW = "see_user_orders";
    private final String ORDERS_TABLE = "orders";
    private final String ORDER_ITEM_TABLE = "order_item";
    private final String USER_ORDER_TABLE = "user_order";
<<<<<<< HEAD
    private final String SOURCE_STR = "source";
    private final String DESTINATION_STR = "destination";

    private final ConnectionPool connectionPool;

    public OrderDAOClass (ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

=======
    private final String ORDER_ACTIVE = "active";
    private final String SOURCE_STR = "source";
    private final String DESTINATION_STR = "destination";
    private final ConnectionPool connectionPool;
    public OrderDAOClass (ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }
>>>>>>> დამატება #47: დაამატეთ OrderDAO ინტერფეისის იმპლემენტაცია (#55)
    private ID getUserID(String userName, Connection con){
        ID id = null;
        String getUserIDQuery = "SELECT id FROM " + USERS_TABLE + "\n"
                + "WHERE user_name = '" + userName + "';";
        try {
            Statement getUserIDStat = con.createStatement();
            ResultSet res = getUserIDStat.executeQuery(getUserIDQuery);
            if(res.next()){
<<<<<<< HEAD
                id = ID.of(res.getInt("id"));
=======
                id = new ID(res.getInt("id"));
>>>>>>> დამატება #47: დაამატეთ OrderDAO ინტერფეისის იმპლემენტაცია (#55)
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }
<<<<<<< HEAD

    @Override
    public Order getOrder(ID orderID) {
        Connection con = connectionPool.acquireConnection();
        String getOrderQuery = "SELECT * FROM " + USER_ORDERS_VIEW + " "
                        + "WHERE order_id = " + orderID.getID() + ";";
=======
    @Override
    public Order getOrder(String userName, ID orderID) {
        Connection con = connectionPool.acquireConnection();
        String getOrderQuery = "SELECT * FROM " + USER_ORDERS_VIEW
                        + "WHERE user_name = '" + userName + "' AND order_id = " + orderID.getID() + ";";
>>>>>>> დამატება #47: დაამატეთ OrderDAO ინტერფეისის იმპლემენტაცია (#55)
        Map<Item, Integer> userGives = new HashMap<>();
        Map<Item, Integer> userTakes = new HashMap<>();
        String orderDate = null;
        String orderStatus = null;
<<<<<<< HEAD
        String userName = null;
=======
>>>>>>> დამატება #47: დაამატეთ OrderDAO ინტერფეისის იმპლემენტაცია (#55)
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
<<<<<<< HEAD
                if (userName == null){
                    userName = res.getString("user_name");
                }
                ItemDAOClass itemDAOClass = new ItemDAOClass(connectionPool);
                Item item =  itemDAOClass.getItem(ID.of(res.getInt("item_id")));;
=======
                ItemDAOClass itemDAOClass = new ItemDAOClass(connectionPool);
                Item item =  itemDAOClass.getItem(new ID(res.getInt("item_id")));;
>>>>>>> დამატება #47: დაამატეთ OrderDAO ინტერფეისის იმპლემენტაცია (#55)
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
<<<<<<< HEAD
                            + USER_ORDERS_VIEW + "WHERE user_name = '" + userName
                            + "' AND order_status ='" + ORDER_ACTIVE + "';";
=======
                            + USER_ORDERS_VIEW + "WHERE user_name = '" + userName + "';";
>>>>>>> დამატება #47: დაამატეთ OrderDAO ინტერფეისის იმპლემენტაცია (#55)
        List<Order> allOrders = new ArrayList<>();
        try {
            Statement getAllOrdersStat = con.createStatement();
            ResultSet res = getAllOrdersStat.executeQuery(getAllOrdersIDQuery);
            while (res.next()){
<<<<<<< HEAD
                Order order = getOrder(ID.of(res.getInt("order_id")));
=======
                Order order = getOrder(userName, new ID(res.getInt("order_id")));
>>>>>>> დამატება #47: დაამატეთ OrderDAO ინტერფეისის იმპლემენტაცია (#55)
                allOrders.add(order);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return allOrders;
    }

    @Override
    public ID addOrder(String userName, Map<ID, Integer> userGives, Map<ID, Integer> userTakes) {
        Connection con = connectionPool.acquireConnection();
        String addOrderInTableQuery = "INSERT INTO " + ORDERS_TABLE + "(order_status)\n"
                                    + "VALUES ('" + ORDER_ACTIVE + "');";
        ID orderID = null;
        try {
            Statement addOrderInTableStat = con.createStatement();
            if(addOrderInTableStat.executeUpdate(addOrderInTableQuery, Statement.RETURN_GENERATED_KEYS) > 0){
                ResultSet generatedID = addOrderInTableStat.getGeneratedKeys();
                if(generatedID.next()){
<<<<<<< HEAD
                    orderID = ID.of(generatedID.getInt(1));
=======
                    orderID = new ID(generatedID.getInt(1));
>>>>>>> დამატება #47: დაამატეთ OrderDAO ინტერფეისის იმპლემენტაცია (#55)
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
<<<<<<< HEAD
        for (ID i: userGives.keySet()) {
=======
        for (ID i: userGives.keySet()
             ) {
>>>>>>> დამატება #47: დაამატეთ OrderDAO ინტერფეისის იმპლემენტაცია (#55)
            String addItemsInOrderSourceQuery = String.format(addItemsInOrderQuery,
                                                                orderID.getID(),
                                                                i.getID(),
                                                                userGives.get(i),
                                                                SOURCE_STR);
            try {
                Statement addItemsInOrderSourceStat = con.createStatement();
                addItemsInOrderSourceStat.executeUpdate(addItemsInOrderSourceQuery);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
<<<<<<< HEAD
        for (ID i: userTakes.keySet()) {
=======
        for (ID i: userTakes.keySet()
        ) {
>>>>>>> დამატება #47: დაამატეთ OrderDAO ინტერფეისის იმპლემენტაცია (#55)
            String addItemsInOrderDestinationeQuery = String.format(addItemsInOrderQuery,
                                                                    orderID.getID(),
                                                                    i.getID(),
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
<<<<<<< HEAD
    public boolean updateOrderStatus(ID orderID, String status) {
        Connection con = connectionPool.acquireConnection();
        String updateOrderStatusQuery = "UPDATE " + ORDERS_TABLE + " SET order_status = '" + status
                                    + "' WHERE order_id = " + orderID.getID();
        return updateQuery(con, updateOrderStatusQuery);
    }

    @Override
    public boolean cancelAllOrders(String userName) {
        Connection con = connectionPool.acquireConnection();
        ID userID = getUserID(userName, con);
        String cancelAllOrdersQuery = "UPDATE " + USER_ORDER_TABLE + " SET order_status = '" + ORDER_INACTIVE
                                    + "' WHERE user_id = " + userID.getID();
        return updateQuery(con, cancelAllOrdersQuery);
    }

    @Override
    public boolean cancelOrder(ID orderID) {
        Connection con = connectionPool.acquireConnection();
        String cancelOrderQuery = "UPDATE " + ORDERS_TABLE + " SET order_status = '" + ORDER_INACTIVE
                                    + "' WHERE order_id = " + orderID.getID();
        return updateQuery(con, cancelOrderQuery);
    }

    private boolean updateQuery(Connection con, String updateQuey) {
        boolean updateBoolean = false;
        try {
            Statement updateStat = con.createStatement();
            if(updateStat.executeUpdate(updateQuey) > 0 )
                updateBoolean = true;
=======
    public boolean removeOrder(String userName, ID orderID) {
        Connection con = connectionPool.acquireConnection();
        String removeOrderQuery = "DELETE FROM " + ORDERS_TABLE + "\n"
                                + "WHERE order_id = " + orderID.getID() + ";";
        boolean removeOrderBoolean = false;
        try {
            Statement removeOrderStat = con.createStatement();
            if (removeOrderStat.executeUpdate(removeOrderQuery) > 0)
                removeOrderBoolean = true;
>>>>>>> დამატება #47: დაამატეთ OrderDAO ინტერფეისის იმპლემენტაცია (#55)
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
<<<<<<< HEAD
        return updateBoolean;
    }

=======
        return removeOrderBoolean;
    }
>>>>>>> დამატება #47: დაამატეთ OrderDAO ინტერფეისის იმპლემენტაცია (#55)
}
