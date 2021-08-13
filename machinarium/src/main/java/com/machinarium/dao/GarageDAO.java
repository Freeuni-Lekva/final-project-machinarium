package com.machinarium.dao;

import com.machinarium.model.Item.Item;
import com.machinarium.model.car.Car;

import java.util.List;

public interface GarageDAO {
	//********** Car **********//
	boolean hasCar(String userName);
	int getCarCount(String userName);

	boolean updateCarName(int carID, String newCarName);
	int addEmptyCar(String userName, String carName); // returns carID
	boolean removeCar(int carID); // removes from garage


	Car getCar(int carID);
	List<Item> getAllCarItems(int carID);
	List<Car> getAllCars(String userName);

	boolean carHasItem(int carID, int itemID);
	boolean addItemToCar(int carID, int itemID);
	boolean removeItemFromCar(int carID, int itemID);



	//********** SpareItem **********//
	boolean hasSpareItem(String userName);
	int getSpareItemCount(String userName);

	boolean hasThisSpareItem(String userName, int itemID);

	boolean addSpareItem(String userName, int itemID);
	boolean removeSpareItem(String userName, int itemID);

	List<Item> getAllSpareItems(String userName);

}
