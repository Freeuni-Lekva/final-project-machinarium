package com.machinarium.dao.implementation;

import com.machinarium.dao.ConnectionPool;
import com.machinarium.dao.ItemDAO;
import com.machinarium.model.Item.Item;
import com.machinarium.model.Item.connector.Connector;
import com.machinarium.model.Item.part.*;
import com.machinarium.utility.common.ID;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOClass implements ItemDAO {
    private final String ITEMS_TABLE = "items";
    private final String CONNECTORS_TABLE = "connectors";
    private final String ITEM_TYPES_TABLE = "item_types";
    private final String ITEMS_VIEW = "see_items";
    private ConnectionPool connectionPool;

    public ItemDAOClass(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }
    @Override
    public Item getItem(ID itemID) {
        Connection con = connectionPool.acquireConnection();

        String getItemFromItemsQuery = "SELECT * FROM " + ITEMS_VIEW + " WHERE item_id = " + itemID.getID() + ";";

        Item item = null;

        try {
            Statement getItemFromItemsStat = con.createStatement();
            ResultSet res = getItemFromItemsStat.executeQuery(getItemFromItemsQuery);

            if(res.next() && res.getString("type_name") != null){ //++
                if(res.getString("type_name").equals("CHASSIS")){
                    item = new Chassis(ID.of(res.getInt("item_id")),
                            res.getString("item_name"),
                            res.getInt("weight"),
                            res.getInt("weight_support"));
                }
                if(res.getString("type_name").equals("BODY")){
                    item = new Body(ID.of(res.getInt("item_id")),
                            res.getString("item_name"),
                            res.getInt("weight"),
                            res.getInt("aero_drag"));
                }
                if(res.getString("type_name").equals("ENGINE")){
                    item = new Engine(ID.of(res.getInt("item_id")),
                            res.getString("item_name"),
                            res.getInt("weight"),
                            res.getInt("horse_power"));
                }
                if(res.getString("type_name").equals("TRANSMISSION")){
                    item = new Transmission(ID.of(res.getInt("item_id")),
                            res.getString("item_name"),
                            res.getInt("weight"));
                }
                if(res.getString("type_name").equals("WHEELS")){
                    item = new Wheels(ID.of(res.getInt("item_id")),
                            res.getString("item_name"),
                            res.getInt("weight"),
                            res.getInt("traction_unit"));
                }
                if(res.getString("type_name").equals("CONNECTOR")){
                    if (res.getString("item_name").equals("Body Mount")) {
                        item = new Connector<>(ID.of(res.getInt("item_id")),
                                res.getString("item_name"),
                                new Chassis(null, null, null, null),
                                new Body(null, null, null, null));
                    }
                    if (res.getString("item_name").equals("Transmission Mount")) {
                        item = new Connector<>(ID.of(res.getInt("item_id")),
                                res.getString("item_name"),
                                new Chassis(null, null, null, null),
                                new Transmission(null, null, null));

                    }
                    if (res.getString("item_name").equals("Suspension")) {
                        item = new Connector<>(ID.of(res.getInt("item_id")),
                                res.getString("item_name"),
                                new Chassis(null, null, null, null),
                                new Wheels(null, null, null, null));

                    }
                    if (res.getString("item_name").equals("Engine Bolts")) {
                        item = new Connector<>(ID.of(res.getInt("item_id")),
                                res.getString("item_name"),
                                new Chassis(null, null, null, null),
                                new Engine(null, null, null, null));
                    }
                    if (res.getString("item_name").equals("Friction Plate")) {
                        item = new Connector<>(ID.of(res.getInt("item_id")),
                                res.getString("item_name"),
                                new Engine(null, null, null, null),
                                new Transmission(null, null, null));
                    }
                    if (res.getString("item_name").equals("Differential")) {
                        item = new Connector<>(ID.of(res.getInt("item_id")),
                                res.getString("item_name"),
                                new Transmission(null, null, null),
                                new Wheels(null, null, null, null));
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        connectionPool.releaseConnection(con);
        return item;
    }

    @Override
    public List<Item> getAllItems() {
        Connection con = connectionPool.acquireConnection();
        String getAllItemsQuery = "SELECT * FROM " + ITEMS_VIEW + ";";
        List<Item> allItems = new ArrayList<>();
        try {
            Statement getAllItemsStat = con.createStatement();
            ResultSet res = getAllItemsStat.executeQuery(getAllItemsQuery);
            while (res.next()){
                allItems.add(getItem(ID.of(res.getInt("item_id"))));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        connectionPool.releaseConnection(con);
        return allItems;
    }
}