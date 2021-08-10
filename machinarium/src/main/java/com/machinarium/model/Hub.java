package com.machinarium.model;

public interface Hub {
	boolean validateCar(String userName, String carNameID);
	boolean validateCars(String userName);
}
