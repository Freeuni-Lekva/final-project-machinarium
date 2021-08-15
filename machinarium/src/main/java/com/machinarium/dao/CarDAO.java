package com.machinarium.dao;

import com.machinarium.model.Item.Item;
import com.machinarium.model.car.Car;
import com.machinarium.utility.common.ID;

import java.util.List;

public interface CarDAO {
    Car getCar(ID carID);
    List<Item> getAllCarItems(ID carID);
    List<Car> getAllCars(String userName);
    boolean updateCarName(ID carID, String newCarName);

    boolean carHasItem(ID carID, ID itemID);
    boolean addItemToCar(ID carID, ID itemID);
    boolean removeItemFromCar(ID carID, ID itemID);
}
