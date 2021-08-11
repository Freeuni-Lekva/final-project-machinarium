package com.machinarium.dao;

import com.machinarium.model.car.Car;
import java.util.List;

public interface CarDAO {

	boolean hasNoCar(String userName);
	int carCount(String userName);

	boolean rename(String userName, String carNameID, String newCarName);
	String declare(String userName, String carName); // returns carNameID
	boolean unDeclare(String userName, String carNameID); // removes empty car from garage


	boolean putItem(String userName, String carNameID, String itemNameID);
	boolean takeItem(String userName, String carNameID, String itemNameID);

	Car getCar(String userName, String carNameID);
	List<String> getAllCarsNameID(String userName);
	List<String> getAllItemNameID(String userName, String carNameID);

}
