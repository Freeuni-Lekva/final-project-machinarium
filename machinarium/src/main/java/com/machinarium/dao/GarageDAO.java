package com.machinarium.dao;

import com.machinarium.model.Item.Item;
import com.machinarium.model.car.Car;
import com.machinarium.utility.common.ID;
import java.util.List;

public interface GarageDAO {
	//********** Car **********//
	boolean hasCar(String userName);
	int getCarCount(String userName);

	boolean updateCarName(ID carID, String newCarName);
	ID addEmptyCar(String userName, String carName); // returns carID
	boolean removeCar(ID carID); // removes from garage


	Car getCar(ID carID);
	List<Item> getAllCarItems(ID carID);
	List<Car> getAllCars(String userName);

	boolean carHasItem(ID carID, ID itemID);
	boolean addItemToCar(ID carID, ID itemID);
	boolean removeItemFromCar(ID carID, ID itemID);



	//********** SpareItem **********//
	boolean hasSpareItem(String userName);
	int getSpareItemCount(String userName);

	boolean hasThisSpareItem(String userName, ID itemID);

	boolean addSpareItem(String userName, ID itemID);
	boolean removeSpareItem(String userName, ID itemID);

	List<Item> getAllSpareItems(String userName);

}
