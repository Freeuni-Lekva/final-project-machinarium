package com.machinarium.dao;

import com.machinarium.model.car.Car;
import java.util.List;

public interface CarDAO {

	boolean hasNoCar(User user);
	int numCars(User user);
	Car getCar(User user, String carNameID);
	List<Car> getCars(User user);

}
