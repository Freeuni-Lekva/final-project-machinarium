package com.machinarium.dao;

import com.machinarium.model.car.Car;
import java.util.List;

public interface CarDAO {

	boolean updateCarName(String userName, int carID, String newCarName);
	int declare(String userName, String carName); // returns carID
	boolean unDeclare(String userName, int carID); // removes empty car from garage

	Car getCar(String userName, int carID);
	List<Integer> getAllItemID(String userName, int carID);
	List<Integer> getAllCarID(String userName);

	boolean containsItem(String userName, int carID, int itemID);
	boolean putItem(String userName, int carID, int itemID);
	boolean takeItem(String userName, int carID, int itemID);

}
