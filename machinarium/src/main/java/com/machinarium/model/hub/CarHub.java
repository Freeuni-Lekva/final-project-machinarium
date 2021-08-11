package com.machinarium.model.hub;

import com.machinarium.dao.CarDAO;
import com.machinarium.dao.ItemDAO;
import com.machinarium.dao.UserDAO;
import com.machinarium.model.car.Car;

import java.util.ArrayList;
import java.util.List;

public class CarHub {
	private UserDAO userDAO;
	private ItemDAO itemDAO;
	private CarDAO carDAO;

	public CarHub(UserDAO userDAO, ItemDAO itemDAO, CarDAO carDAO) {
		this.userDAO = userDAO;
		this.itemDAO = itemDAO;
		this.carDAO = carDAO;
	}

//	public List<Car> getCars(String userName) {
//		List<Car> allCars = new ArrayList<>();
//
//		List<String> allCarsNameID = carDAO.getAllCarsNameID(userName);
//		for (String carNameID : allCarsNameID) {
//			Car curCar = carDAO.getCar(userName, carNameID);
//			allCars.add(curCar);
//		}
//
//		return allCars;
//	}
//
//	public boolean disassembleCar(String userName, String carNameID) {
//		if (carDAO.hasNoCar(userName)) return false;
//
//		List<String> allItems = carDAO.getAllItems(userName, carNameID);
//		if (allItems == null || allItems.isEmpty()) return false;
//
//		for (String curItem : allItems) {
//			carDAO.takeItem(userName, carNameID, curItem);
//		}
//
//
//
//		return true;
//	}


}
