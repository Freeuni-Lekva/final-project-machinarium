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
import java.util.List;

public class GarageDAOClass implements GarageDAO {
    private final String USER_CARS_VIEW = "see_user_cars";
    private final String CAR_PARTS_VIEW = "car_parts";
    private final String USERS_TABLE = "users";
    private final String CARS_TABLE = "cars";
    private ConnectionPool connectionPool;
    public GarageDAOClass(ConnectionPool connectionPool){
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
                id = new ID(res.getInt("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return id;
    }

    @Override
    public boolean hasCar(String userName) {
        Connection con = connectionPool.acquireConnection();
        ID userID = getUserID(userName);
        Boolean hasCarBoolean = false;
        if (userID != null){
            String hasCarQuery = "SELECT * FROM " + USER_CARS_VIEW + "\n"
                                + "WHERE user_id = " + userID.getID() + ";";
            try {
                Statement hasCarStat = con.createStatement();
                ResultSet res = hasCarStat.executeQuery(hasCarQuery);
                if(res.next()){
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
        ID userID = getUserID(userName);
        int carCount = 0;
        if(userID != null){
            String getCarCountQuery = "SELECT * FROM " + USER_CARS_VIEW + "\n"
                    + "WHERE user_id = " + userID.getID() + ";";
            try {
                Statement getCarCountStat = con.createStatement();
                ResultSet res = getCarCountStat.executeQuery(getCarCountQuery);
                while (res.next()){
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
        boolean updateCarNameBoolean = false;
        String updateCarNameQuery = "UPDATE " + CARS_TABLE + "\n"
                                    + "SET car_name = '" + newCarName + "'" + "\n"
                                    + "WHERE id = " + carID.getID() + ";";
        try {
            Statement updateCarNameStat = con.createStatement();
            if (updateCarNameStat.executeUpdate(updateCarNameQuery) > 0){
                updateCarNameBoolean = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return updateCarNameBoolean;
    }

    @Override
    public ID addEmptyCar(String userName, String carName) {
        Connection con = connectionPool.acquireConnection();
        connectionPool.releaseConnection(con);
        return null;
    }

    @Override
    public boolean removeCar(ID carID) {
        Connection con = connectionPool.acquireConnection();
        String removeCarQuery = "DELETE FROM " + CARS_TABLE + "\n"
                                + "WHERE id = " + carID.getID() + ";";
        boolean removeCarBoolean = false;
        try {
            Statement removeCarStat = con.createStatement();
            if(removeCarStat.executeUpdate(removeCarQuery) > 0){
                removeCarBoolean = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        connectionPool.releaseConnection(con);
        return removeCarBoolean;
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
                    id = new ID(res.getInt("car_id"));
                    name = res.getString("car_name");
                }
                if(res.getString("item_name").equals("CHASSIS")){
                    chassis = new Chassis(new ID(res.getInt("item_id")),
                            res.getString("item_name"),
                            res.getInt("weight"),
                            res.getInt("weight_support"));
                }
                if(res.getString("item_name").equals("BODY")){
                    body = new Body(new ID(res.getInt("item_id")),
                            res.getString("item_name"),
                            res.getInt("weight"),
                            res.getInt("aero_drag"));
                }
                if(res.getString("item_name").equals("ENGINE")){
                    engine = new Engine(new ID(res.getInt("item_id")),
                            res.getString("item_name"),
                            res.getInt("weight"),
                            res.getInt("horse_power"));
                }
                if(res.getString("item_name").equals("TRANSMISSION")){
                    transmission = new Transmission(new ID(res.getInt("item_id")),
                            res.getString("item_name"),
                            res.getInt("weight"));
                }
                if(res.getString("item_name").equals("WHEELS")){
                    wheels = new Wheels(new ID(res.getInt("item_id")),
                            res.getString("item_name"),
                            res.getInt("weight"),
                            res.getInt("traction_unit"));
                }
                if(res.getString("connector_name").equals("Body Mount")){
                    chassisBody = new Connector<>(new ID(res.getInt("connector_id")),
                            res.getString("connector_name"),
                            new Chassis(new ID(res.getInt("item_type_1_id")),
                                    res.getString("it1_item_name"),
                                    res.getInt("it1_weight"),
                                    res.getInt("it1_weight_support")),
                            new Body(new ID(res.getInt("item_type_2_id")),
                                    res.getString("it2_item_name"),
                                    res.getInt("it2_weight"),
                                    res.getInt("it2_aero_drag")));
                }
                if(res.getString("connector_name").equals("Transmission Mount")){
                    chassisTransmission = new Connector<>(new ID(res.getInt("connector_id")),
                            res.getString("connector_name"),
                            new Chassis(new ID(res.getInt("item_type_1_id")),
                                    res.getString("it1_item_name"),
                                    res.getInt("it1_weight"),
                                    res.getInt("it1_weight_support")),
                            new Transmission(new ID(res.getInt("item_type_2_id")),
                                    res.getString("it2_item_name"),
                                    res.getInt("it2_weight")));

                }
                if(res.getString("connector_name").equals("Suspension")){
                    chassisWheels = new Connector<>(new ID(res.getInt("connector_id")),
                            res.getString("connector_name"),
                            new Chassis(new ID(res.getInt("item_type_1_id")),
                                    res.getString("it1_item_name"),
                                    res.getInt("it1_weight"),
                                    res.getInt("it1_weight_support")),
                            new Wheels(new ID(res.getInt("item_type_2_id")),
                                    res.getString("it2_item_name"),
                                    res.getInt("it2_weight"),
                                    res.getInt("it2_traction_unit")));

                }
                if(res.getString("connector_name").equals("Engine Bolts")){
                    chassisEngine = new Connector<>(new ID(res.getInt("connector_id")),
                            res.getString("connector_name"),
                            new Chassis(new ID(res.getInt("item_type_1_id")),
                                    res.getString("it1_item_name"),
                                    res.getInt("it1_weight"),
                                    res.getInt("it1_weight_support")),
                            new Engine(new ID(res.getInt("item_type_2_id")),
                                    res.getString("it2_item_name"),
                                    res.getInt("it2_weight"),
                                    res.getInt("it2_horse_power")));
                }
                if(res.getString("connector_name").equals("Friction Plate")){
                    engineTransmission = new Connector<>(new ID(res.getInt("connector_id")),
                            res.getString("connector_name"),
                            new Engine(new ID(res.getInt("item_type_1_id")),
                                    res.getString("it1_item_name"),
                                    res.getInt("it1_weight"),
                                    res.getInt("it1_horse_power")),
                            new Transmission(new ID(res.getInt("item_type_2_id")),
                                    res.getString("it2_item_name"),
                                    res.getInt("it2_weight")));
                }
                if(res.getString("connector_name").equals("Differential")){
                    transmissionWheels = new Connector<>(new ID(res.getInt("connector_id")),
                            res.getString("connector_name"),
                            new Transmission(new ID(res.getInt("item_type_1_id")),
                                    res.getString("it1_item_name"),
                                    res.getInt("it1_weight")),
                            new Wheels(new ID(res.getInt("item_type_2_id")),
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
        connectionPool.releaseConnection(con);
        return null;
    }

    @Override
    public List<Car> getAllCars(String userName) {
        Connection con = connectionPool.acquireConnection();
        connectionPool.releaseConnection(con);
        return null;
    }

    @Override
    public boolean carHasItem(ID carID, ID itemID) {
        Connection con = connectionPool.acquireConnection();
        connectionPool.releaseConnection(con);
        return false;
    }

    @Override
    public boolean addItemToCar(ID carID, ID itemID) {
        Connection con = connectionPool.acquireConnection();
        connectionPool.releaseConnection(con);
        return false;
    }

    @Override
    public boolean removeItemFromCar(ID carID, ID itemID) {
        Connection con = connectionPool.acquireConnection();
        connectionPool.releaseConnection(con);
        return false;
    }

    @Override
    public boolean hasSpareItem(String userName) {
        Connection con = connectionPool.acquireConnection();
        connectionPool.releaseConnection(con);
        return false;
    }

    @Override
    public int getSpareItemCount(String userName) {
        Connection con = connectionPool.acquireConnection();
        connectionPool.releaseConnection(con);
        return 0;
    }

    @Override
    public boolean hasThisSpareItem(String userName, ID itemID) {
        Connection con = connectionPool.acquireConnection();
        connectionPool.releaseConnection(con);
        return false;
    }

    @Override
    public boolean addSpareItem(String userName, ID itemID) {
        Connection con = connectionPool.acquireConnection();
        connectionPool.releaseConnection(con);
        return false;
    }

    @Override
    public boolean removeSpareItem(String userName, ID itemID) {
        Connection con = connectionPool.acquireConnection();
        connectionPool.releaseConnection(con);
        return false;
    }

    @Override
    public List<Item> getAllSpareItems(String userName) {
        Connection con = connectionPool.acquireConnection();
        connectionPool.releaseConnection(con);
        return null;
    }
}
