package com.machinarium.dao.implementation;

import com.machinarium.dao.ConnectionPool;
import com.machinarium.dao.GarageDAO;
import com.machinarium.model.Item.Item;
import com.machinarium.model.Item.connector.Connector;
import com.machinarium.model.Item.part.*;
import com.machinarium.model.car.Car;
import com.machinarium.model.car.DragCar;
import com.machinarium.utility.common.ID;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GarageDAOClass implements GarageDAO {
    private final String USER_CARS_VIEW = "see_user_cars";
    private final String CAR_PARTS_VIEW = "see_car_parts";
    private final String USER_ITEMS_VIEW = "see_user_items";
    private final String CARS_TABLE = "cars";
    private final String USERS_TABLE = "users";
    private final String CAR_PARTS_TABLE = "car_parts";
    private final String USER_GARAGE_TABLE = "user_garage";
    private final String GARAGE_ITEM_TABLE = "garage_item";
    private final ConnectionPool connectionPool;

    public GarageDAOClass(ConnectionPool connectionPool){
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
                id = ID.of(res.getInt("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    private boolean updateQuery(Connection con, String query) {
        boolean updateBoolean = false;
        try {
            Statement addItemToCarStatement = con.createStatement();
            if(addItemToCarStatement.executeUpdate(query) > 0){
                updateBoolean = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        connectionPool.releaseConnection(con);
        return updateBoolean;
    }

    @Override
    public boolean hasCar(String userName) {
        Connection con = connectionPool.acquireConnection();
        ID userID = getUserID(userName, con);
        boolean hasCarBoolean = false;
        if (userID != null){
            String hasCarQuery = "SELECT * FROM " + USER_CARS_VIEW + "\n"
                                + "WHERE user_id = " + userID.getID() + ";";
            try {
                Statement hasCarStat = con.createStatement();
                ResultSet res = hasCarStat.executeQuery(hasCarQuery);
                if(res.next()){
                    if (res.getString("car_name") != null)
                        hasCarBoolean = true;
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        connectionPool.releaseConnection(con);
        return hasCarBoolean;
    }

    @Override
    public int getCarCount(String userName) {
        Connection con = connectionPool.acquireConnection();
        ID userID = getUserID(userName, con);
        int carCount = 0;
        if(userID != null){
            String getCarCountQuery = "SELECT * FROM " + USER_CARS_VIEW + "\n"
                    + "WHERE user_id = " + userID.getID() + ";";
            try {
                Statement getCarCountStat = con.createStatement();
                ResultSet res = getCarCountStat.executeQuery(getCarCountQuery);
                while (res.next()){
                    if (res.getString("car_name") != null)
                        carCount++;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        connectionPool.releaseConnection(con);
        return carCount;
    }

    @Override
    public boolean updateCarName(ID carID, String newCarName) {
        Connection con = connectionPool.acquireConnection();
        String updateCarNameQuery = "UPDATE " + CARS_TABLE + "\n"
                                    + "SET car_name = '" + newCarName + "'" + "\n"
                                    + "WHERE id = " + carID.getID() + ";";
        return updateQuery(con, updateCarNameQuery);
    }

    @Override
    public ID addEmptyCar(String userName, String carName) {
        Connection con = connectionPool.acquireConnection();
        ID userID = getUserID(userName, con);
        String getGarageIDQuery = "SELECT * FROM " + USER_GARAGE_TABLE
                                + " WHERE user_id = " + userID.getID() + ";";
        int garage_id = 0;
        try {
            Statement getGarageIDStat = con.createStatement();
            ResultSet res = getGarageIDStat.executeQuery(getGarageIDQuery);
            if(res.next()){
                garage_id = res.getInt("garage_id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String addEmptyCarQuery = "INSERT INTO " + CARS_TABLE + "(car_name)\n" + "VALUES('" + carName + "');";
        boolean addEmptyCarBoolean = false;
        ID carID = null;
        try {
            Statement addEmptyCarStat = con.createStatement();
            if(addEmptyCarStat.executeUpdate(addEmptyCarQuery) > 0){
                addEmptyCarBoolean = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // COMMIT; DDL
        if(addEmptyCarBoolean){
            String getCarIDQuery = "SELECT id FROM " + CARS_TABLE + " WHERE car_name = '" + carName + "';";
            try {
                Statement getCarIDStat = con.createStatement();
                ResultSet res = getCarIDStat.executeQuery(getCarIDQuery);
                if(res.next()){
                    carID = ID.of(res.getInt("id"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            String addCarInGarageQuery = "INSERT INTO garage_car(garage_id, car_id) VALUES ("
                                        + garage_id + ", " + carID.getID() + ");";
            try {
                Statement  addCarInGarageStat = con.createStatement();
                if (addCarInGarageStat.executeUpdate(addCarInGarageQuery) <= 0){
                    addEmptyCarBoolean = false;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        connectionPool.releaseConnection(con);
        return carID;
    }

    @Override
    public boolean removeCar(ID carID) {
        Connection con = connectionPool.acquireConnection();
        String removeCarQuery = "DELETE FROM " + CARS_TABLE + "\n"
                                + "WHERE id = " + carID.getID() + ";";
        return updateQuery(con, removeCarQuery);
    }

    @Override
    public Car getCar(ID carID) {
        Connection con = connectionPool.acquireConnection();
        String getCarQuery = "SELECT * FROM " + CAR_PARTS_VIEW + "\n"
                            + "WHERE car_id = " + carID.getID() + ";";
        Car car = null;
        try {
            Statement getCarStat = con.createStatement();
            ResultSet res = getCarStat.executeQuery(getCarQuery);
            ID id = null;
            String name = null;

            // Car Parts
            Chassis chassis = null;
            Body body = null;
            Engine engine = null;
            Transmission transmission = null;
            Wheels wheels = null;
            Connector<Chassis, Body> chassisBody = null;
            Connector<Chassis, Transmission> chassisTransmission = null;
            Connector<Chassis, Wheels> chassisWheels = null;
            Connector<Chassis, Engine> chassisEngine = null;
            Connector<Engine, Transmission> engineTransmission= null;
            Connector<Transmission, Wheels> transmissionWheels = null;

            while(res.next()){
                if(id == null){
                    id = ID.of(res.getInt("car_id"));
                    name = res.getString("car_name");
                }
                if(res.getString("item_name").equals("CHASSIS")){
                    chassis = new Chassis(ID.of(res.getInt("item_id")),
                            res.getString("item_name"),
                            res.getInt("weight"),
                            res.getInt("weight_support"));
                }
                if(res.getString("item_name").equals("BODY")){
                    body = new Body(ID.of(res.getInt("item_id")),
                            res.getString("item_name"),
                            res.getInt("weight"),
                            res.getInt("aero_drag"));
                }
                if(res.getString("item_name").equals("ENGINE")){
                    engine = new Engine(ID.of(res.getInt("item_id")),
                            res.getString("item_name"),
                            res.getInt("weight"),
                            res.getInt("horse_power"));
                }
                if(res.getString("item_name").equals("TRANSMISSION")){
                    transmission = new Transmission(ID.of(res.getInt("item_id")),
                            res.getString("item_name"),
                            res.getInt("weight"));
                }
                if(res.getString("item_name").equals("WHEELS")){
                    wheels = new Wheels(ID.of(res.getInt("item_id")),
                            res.getString("item_name"),
                            res.getInt("weight"),
                            res.getInt("traction_unit"));
                }
                if(res.getString("connector_name").equals("Body Mount")){
                    chassisBody = new Connector<>(ID.of(res.getInt("connector_id")),
                            res.getString("connector_name"),
                            new Chassis(ID.of(res.getInt("item_type_1_id")),
                                    res.getString("it1_item_name"),
                                    res.getInt("it1_weight"),
                                    res.getInt("it1_weight_support")),
                            new Body(ID.of(res.getInt("item_type_2_id")),
                                    res.getString("it2_item_name"),
                                    res.getInt("it2_weight"),
                                    res.getInt("it2_aero_drag")));
                }
                if(res.getString("connector_name").equals("Transmission Mount")){
                    chassisTransmission = new Connector<>(ID.of(res.getInt("connector_id")),
                            res.getString("connector_name"),
                            new Chassis(ID.of(res.getInt("item_type_1_id")),
                                    res.getString("it1_item_name"),
                                    res.getInt("it1_weight"),
                                    res.getInt("it1_weight_support")),
                            new Transmission(ID.of(res.getInt("item_type_2_id")),
                                    res.getString("it2_item_name"),
                                    res.getInt("it2_weight")));

                }
                if(res.getString("connector_name").equals("Suspension")){
                    chassisWheels = new Connector<>(ID.of(res.getInt("connector_id")),
                            res.getString("connector_name"),
                            new Chassis(ID.of(res.getInt("item_type_1_id")),
                                    res.getString("it1_item_name"),
                                    res.getInt("it1_weight"),
                                    res.getInt("it1_weight_support")),
                            new Wheels(ID.of(res.getInt("item_type_2_id")),
                                    res.getString("it2_item_name"),
                                    res.getInt("it2_weight"),
                                    res.getInt("it2_traction_unit")));

                }
                if(res.getString("connector_name").equals("Engine Bolts")){
                    chassisEngine = new Connector<>(ID.of(res.getInt("connector_id")),
                            res.getString("connector_name"),
                            new Chassis(ID.of(res.getInt("item_type_1_id")),
                                    res.getString("it1_item_name"),
                                    res.getInt("it1_weight"),
                                    res.getInt("it1_weight_support")),
                            new Engine(ID.of(res.getInt("item_type_2_id")),
                                    res.getString("it2_item_name"),
                                    res.getInt("it2_weight"),
                                    res.getInt("it2_horse_power")));
                }
                if(res.getString("connector_name").equals("Friction Plate")){
                    engineTransmission = new Connector<>(ID.of(res.getInt("connector_id")),
                            res.getString("connector_name"),
                            new Engine(ID.of(res.getInt("item_type_1_id")),
                                    res.getString("it1_item_name"),
                                    res.getInt("it1_weight"),
                                    res.getInt("it1_horse_power")),
                            new Transmission(ID.of(res.getInt("item_type_2_id")),
                                    res.getString("it2_item_name"),
                                    res.getInt("it2_weight")));
                }
                if(res.getString("connector_name").equals("Differential")){
                    transmissionWheels = new Connector<>(ID.of(res.getInt("connector_id")),
                            res.getString("connector_name"),
                            new Transmission(ID.of(res.getInt("item_type_1_id")),
                                    res.getString("it1_item_name"),
                                    res.getInt("it1_weight")),
                            new Wheels(ID.of(res.getInt("item_type_2_id")),
                                    res.getString("it2_item_name"),
                                    res.getInt("it2_weight"),
                                    res.getInt("it2_traction_unit")));
                }

            }
            car = new DragCar(id, name, chassis, body, engine, transmission, wheels,
                    chassisBody, chassisTransmission, chassisWheels, chassisEngine,
                    engineTransmission, transmissionWheels);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return car;
    }

    @Override
    public List<Item> getAllCarItems(ID carID) {
        Connection con = connectionPool.acquireConnection();
        String getAllCarItemsQuery = "SELECT * FROM " + CAR_PARTS_VIEW + "\n"
                                    + "WHERE car_id = " + carID.getID() + ";";

        List<Item> allCarItems = new ArrayList<>();
        try {
            Statement allCarItemsStat = con.createStatement();
            ResultSet res = allCarItemsStat.executeQuery(getAllCarItemsQuery);
            ItemDAOClass itemDAO = new ItemDAOClass(connectionPool);
            while(res.next()){
                Item item = itemDAO.getItem(ID.of(res.getInt("item_id")));
                allCarItems.add(item);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        connectionPool.releaseConnection(con);
        return allCarItems;
    }

    @Override
    public List<Car> getAllCars(String userName) {
        Connection con = connectionPool.acquireConnection();
        ID userID = getUserID(userName, con);
        String getAllCarsQuery = "SELECT * FROM "+ USER_CARS_VIEW + "\n"
                                + "WHERE user_id = " + userID.getID() + ";";
        List<Car> allCars = new ArrayList<>();
        try {
            Statement getAllCarsStat = con.createStatement();
            ResultSet res = getAllCarsStat.executeQuery(getAllCarsQuery);
            while (res.next()){
                if (res.getString("car_name") != null)
                    allCars.add(getCar(ID.of(res.getInt("car_id"))));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        connectionPool.releaseConnection(con);
        return allCars;
    }

    @Override
    public boolean carHasItem(ID carID, ID itemID) {
        Connection con = connectionPool.acquireConnection();
        String carHasItemQuery = "SELECT * FROM " + CAR_PARTS_VIEW + "\n"
                                +"WHERE car_id = " + carID.getID() + ";";
        boolean carHasItemBoolean = false;
        try {
            Statement carHasItemStat = con.createStatement();
            ResultSet res = carHasItemStat.executeQuery(carHasItemQuery);
            while(res.next()){
                if(res.getInt("item_id") == itemID.getID()){
                    carHasItemBoolean = true;
                    break;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return carHasItemBoolean;
    }

    @Override
    public boolean addItemToCar(ID carID, ID itemID) {
        Connection con = connectionPool.acquireConnection();
        String addItemToCarQuery = "INSERT INTO " + CAR_PARTS_TABLE + "(car_id, item_id)"+ "\n"
                                    +"VALUES (" + carID.getID() + ", " + itemID.getID()+");";
        return updateQuery(con, addItemToCarQuery);
    }


    @Override
    public boolean removeItemFromCar(ID carID, ID itemID) {
        Connection con = connectionPool.acquireConnection();
        String removeItemFromCarQuery = "DELETE FROM " + CAR_PARTS_TABLE + "\n" +
                                        "WHERE car_id = " + carID.getID()
                                        + " AND item_id = " + itemID.getID() + ";";
        return updateQuery(con, removeItemFromCarQuery);
    }

    @Override
    public boolean hasSpareItem(String userName) {
        Connection con = connectionPool.acquireConnection();
        String hasSpareItemQuery = "SELECT * FROM " + USER_ITEMS_VIEW + "\n"
                                    +"WHERE user_name = '" + userName + "';";

        boolean hasSpareItemBoolean = false;
        try {
            Statement hasSpareItemStat = con.createStatement();
            ResultSet res = hasSpareItemStat.executeQuery(hasSpareItemQuery);
            while (res.next()){
                if(res.getInt("item_count") > 0){
                    hasSpareItemBoolean = true;
                    break;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        connectionPool.releaseConnection(con);
        return hasSpareItemBoolean;
    }

    @Override
    public int getSpareItemCount(String userName) {
        Connection con = connectionPool.acquireConnection();
        String getSpareItemCountQuery = "SELECT * FROM " + USER_ITEMS_VIEW + "\n"
                                        +"WHERE user_name = '" + userName + "';";
        int spareItemCount = 0;
        try {
            Statement getSpareItemCountStat = con.createStatement();
            ResultSet res = getSpareItemCountStat.executeQuery(getSpareItemCountQuery);
            while (res.next()){
                spareItemCount += res.getInt("item_count");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return spareItemCount;
    }

    @Override
    public boolean hasThisSpareItem(String userName, ID itemID) {
        Connection con = connectionPool.acquireConnection();
        String hasThisSpareItemQuery = "SELECT * FROM " + USER_ITEMS_VIEW + "\n"
                                    + "WHERE user_name = '" + userName + "' "
                                    + "AND item_id = " + itemID.getID() + ";";
        boolean hasThisSpareItemBoolean = false;
        try {
            Statement hasThisSpareItemStat = con.createStatement();
            ResultSet res = hasThisSpareItemStat.executeQuery(hasThisSpareItemQuery);
            if(res.next()){
                if(res.getInt("item_count") > 0){
                    hasThisSpareItemBoolean = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return hasThisSpareItemBoolean;
    }

    @Override
    public boolean addSpareItem(String userName, ID itemID) {
        Connection con = connectionPool.acquireConnection();
        boolean hasSpareItemAddBoolean = false;
        String hasSpareItemAddQuery = "SELECT * FROM " + USER_ITEMS_VIEW + "\n"
                                    + "WHERE user_name = '" + userName + "' "
                                    + "AND item_id = " + itemID.getID() + ";";
        ID userID = getUserID(userName, con);
        int garage_id = 0;
        int item_count = 0;
        try {
            Statement hasSpareItemAddStat = con.createStatement();
            ResultSet res = hasSpareItemAddStat.executeQuery(hasSpareItemAddQuery);
            if(res.next()){
                hasSpareItemAddBoolean = true;
                garage_id = res.getInt("garage_id");
                item_count = res.getInt("item_count");
            }else {
                String userGarageIDQuery = "SELECT garage_id FROM " + USER_GARAGE_TABLE + "\n"
                                        + "WHERE user_id = " + userID.getID() + ";";
                Statement userGarageIDStat = con.createStatement();
                ResultSet resGarageID = userGarageIDStat.executeQuery(userGarageIDQuery);
                if(resGarageID.next()){
                    garage_id = resGarageID.getInt("garage_id");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String addSpareItemQuery;
        if (hasSpareItemAddBoolean){
            addSpareItemQuery = "UPDATE " + GARAGE_ITEM_TABLE + "\n"
                            + "SET item_count = " + (item_count + 1) + "\n"
                            + "WHERE garage_id = " + garage_id
                            + " AND item_id = " + itemID.getID() + ";";
        }else {
            addSpareItemQuery = "INSERT INTO " + GARAGE_ITEM_TABLE + "(garage_id, item_id, item_count)\n"
                                + "VALUES (" + garage_id + " ," + itemID.getID() + ", 1);";
        }

        return updateQuery(con, addSpareItemQuery);
    }

    @Override
    public boolean removeSpareItem(String userName, ID itemID) {
        Connection con = connectionPool.acquireConnection();
        String garageIdQuery = "SELECT * FROM " + USER_ITEMS_VIEW + "\n"
                            + "WHERE user_name = '" + userName + "' "
                            + "AND item_id = " + itemID.getID() + ";";
        int garage_id = -1;
        int item_count = -1;
        try {
            Statement hasSpareItemAddStat = con.createStatement();
            ResultSet res = hasSpareItemAddStat.executeQuery(garageIdQuery);
            if(res.next()){
                garage_id = res.getInt("garage_id");
                item_count = res.getInt("item_count");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String removeSpareItemQuery;
        if(item_count > 0){
            removeSpareItemQuery = "UPDATE " + GARAGE_ITEM_TABLE + "\n"
                                    + "SET item_count = " + (item_count-1) + "\n"
                                    + "WHERE garage_id = " + garage_id
                                    + " AND item_id = " + itemID.getID() + ";";

        }else{
            removeSpareItemQuery = "DELETE FROM " + GARAGE_ITEM_TABLE + " \n"
                                + "WHERE garage_id = " + garage_id + " AND item_id = " + itemID.getID() + ";";
        }

        return updateQuery(con, removeSpareItemQuery);
    }

    @Override
    public Map<Item, Integer> getAllSpareItems(String userName) {
        Connection con = connectionPool.acquireConnection();
        String getAllSpareItemsQuery = "SELECT * FROM " + USER_ITEMS_VIEW + "\n"
                                    + "WHERE user_name = '" + userName + "';";

        Map<Item, Integer> allSpareItems = new HashMap<>();
        try {
            Statement getAllSpareItemsStat = con.createStatement();
            ResultSet res = getAllSpareItemsStat.executeQuery(getAllSpareItemsQuery);
            ItemDAOClass itemDAO = new ItemDAOClass(connectionPool);
            while (res.next()){
                Item item = itemDAO.getItem(ID.of(res.getInt("item_id")));
                if(item != null) allSpareItems.put(item, res.getInt("item_count"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        connectionPool.releaseConnection(con);
        return allSpareItems;
    }
}
