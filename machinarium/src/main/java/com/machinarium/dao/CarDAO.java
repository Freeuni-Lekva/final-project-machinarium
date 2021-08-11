package com.machinarium.dao;

import com.machinarium.model.car.Car;
import java.util.List;

public interface CarDAO {

	boolean hasNoCar(String userName);
	int numCars(String userName);
	Car getCar(String userName, String carNameID);
	List<Car> getCars(String userName);

	boolean addCar(Car car);

}
