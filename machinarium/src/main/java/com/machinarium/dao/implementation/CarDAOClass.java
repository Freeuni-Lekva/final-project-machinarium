package com.machinarium.dao.implementation;

import com.machinarium.dao.CarDAO;
import com.machinarium.dao.ConnectionPool;
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
import java.util.List;

public class CarDAOClass implements CarDAO {

    private final String USER_CARS_VIEW = "see_user_cars";
    private final String CAR_PARTS_VIEW = "see_car_parts";
    private final String USERS_TABLE = "users";
    private final String CARS_TABLE = "cars";
    private final String CAR_PARTS_TABLE = "car_parts";
    private ConnectionPool connectionPool;

    public CarDAOClass(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }
    private ID getUserID(String userName){
        Connection con = connectionPool.acquireConnection();
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
        connectionPool.releaseConnection(con);
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
            while(res.next()){
                Item item = new Item(ID.of(res.getInt("item_id")),
                        res.getString("item_name"));
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
        String getAllCarsQuery = "SELECT * FROM "+USER_CARS_VIEW + "\n"
                + "WHERE user_name = '" + userName + "';";
        List<Car> allCars = new ArrayList<>();
        try {
            Statement getAllCarsStat = con.createStatement();
            ResultSet res = getAllCarsStat.executeQuery(getAllCarsQuery);
            while (res.next()){
                allCars.add(getCar(ID.of(res.getInt("car_id"))));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        connectionPool.releaseConnection(con);
        return null;
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
}
