package com.machinarium.model.hub;

import com.machinarium.dao.*;
import com.machinarium.model.car.Car;

import java.util.ArrayList;
import java.util.List;

public class CarHub {
//	private final CarDAO carDAO;
//	private final SpareItemDAO spareItemDAO;
//	private final ItemDAO itemDAO;
//	private final UserDAO userDAO;
//	private final OrderDAO orderDAO;
//	private final StatsDAO statsDAO;
//	private final RewardsDAO rewardsDAO;
//
//
//	public CarHub(CarDAO carDAO, SpareItemDAO spareItemDAO, ItemDAO itemDAO,
//				  UserDAO userDAO, OrderDAO orderDAO,
//				  StatsDAO statsDAO, RewardsDAO rewardsDAO) {
//		this.carDAO = carDAO;
//		this.spareItemDAO = spareItemDAO;
//		this.itemDAO = itemDAO;
//		this.userDAO = userDAO;
//		this.orderDAO = orderDAO;
//		this.statsDAO = statsDAO;
//		this.rewardsDAO = rewardsDAO;
//	}


//	public List<Car> getAllCars(String userName) {
//		List<Car> allCars = new ArrayList<>();
//
//		List<Integer> allCarID = carDAO.getAllCarID(userName);
//		for (Integer curCarID : allCarID) {
//			Car curCar = carDAO.getCar(curCarID);
//			allCars.add(curCar);
//		}
//
//		return allCars;
//	}

	// returns new carID
//	public int constructCar(String userName, String newCarName, List<Integer> itemIDs) {
//		if (userDAO.getUser(userName) == null) return Car.NONE_ID;
//		if (newCarName == null) return Car.NONE_ID;
//		if (itemIDs.isEmpty()) return Car.NONE_ID;
//
//		int newCarID = carDAO.addEmptyCar(userName, newCarName);
//		if (newCarID == Car.NONE_ID) return Car.NONE_ID;
//
//		for (Integer curItemID : itemIDs) {
//			carDAO.addItem(newCarID, curItemID);
//		}
//
//		return newCarID;
//	}

//	public boolean disassembleCar(String userName, int carID) {
//		if (carDAO.hasCar(userName)) return false;
//
//		List<Integer> allItemID = carDAO.getAllItemID(userName, carID);
//		if (allItemID == null || allItemID.isEmpty()) return false;
//
//		for (Integer curItemID : allItemID) {
//			carDAO.removeItem(carID, curItemID);
//		}
//
//		return true;
//	}

}
