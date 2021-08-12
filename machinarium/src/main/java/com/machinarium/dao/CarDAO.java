package com.machinarium.dao;

import com.machinarium.model.Item.Item;
import com.machinarium.model.car.Car;
import java.util.List;

public interface CarDAO {
	boolean hasCar(String userName);  //ToDo: export to UserDAO
	int carCount(String userName);  //ToDo: export to UserDAO

	boolean updateCarName(String userName, int carID, String newCarName);
	int addEmptyCar(String userName, String carName); // returns carID
	boolean removeEmptyCar(String userName, int carID); // removes empty car from garage

	Car getCar(String userName, int carID);
	List<Integer> getAllItemID(String userName, int carID);
	List<Item> getAllItems(String userName, int carID); //++ //ToDo: Review
	List<Integer> getAllCarID(String userName);
	List<Car> getAllCars(String userName); //++ //ToDo: Review

	boolean hasItem(String userName, int carID, int itemID);
	boolean addItem(String userName, int carID, int itemID);
	boolean removeItem(String userName, int carID, int itemID);

}
