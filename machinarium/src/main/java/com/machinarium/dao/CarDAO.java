package com.machinarium.dao;

import com.machinarium.model.car.Car;
import java.util.List;

public interface CarDAO {

	boolean hasNoCar(String userName);
	int carCount(String userName);

	boolean rename(String userName, int carUid, String newCarName);
	int declare(String userName, String carName); // returns carUid
	boolean unDeclare(String userName, int carUid); // removes empty car from garage

	List<Integer> getAllCarUid(String userName);
	Car getCar(String userName, int carUid);
	List<Integer> getAllItemUid(String userName, int carUid);

	boolean containsItem(String userName, int carUid, int itemUid);
	boolean putItem(String userName, int carUid, int itemUid);
	boolean takeItem(String userName, int carUid, int itemUid);



}
