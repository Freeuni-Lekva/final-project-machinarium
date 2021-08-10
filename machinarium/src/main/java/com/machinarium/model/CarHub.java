package com.machinarium.model;

import com.machinarium.dao.CarDAO;
import com.machinarium.dao.ItemDAO;
import com.machinarium.dao.UserDAO;
import com.machinarium.model.car.Car;

public class CarHub {
	private UserDAO userDAO;
	private ItemDAO itemDAO;
	private CarDAO carDAO;

	public CarHub(UserDAO userDAO, ItemDAO itemDAO, CarDAO carDAO) {
		this.userDAO = userDAO;
		this.itemDAO = itemDAO;
		this.carDAO = carDAO;
	}

	public boolean validateCar(String userName, String carNameID) {
		Car car = carDAO.getCar(userName, carNameID);
		return car.isValid();
	}



}
