package com.machinarium.dao;

import com.machinarium.model.car.Car;

import java.util.List;

public interface CarDAO {

	boolean hasNoCar(String userName);
	int numCars(String userName);

	List<String> getAllCarsNameID(String userName);
	Car getCar(String userName, String carNameID);

	boolean addCar(Car car);

}
