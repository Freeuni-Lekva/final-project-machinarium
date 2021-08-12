package com.machinarium.model.hub;

import com.machinarium.dao.*;
import com.machinarium.model.car.Car;

import java.util.ArrayList;
import java.util.List;

public class CarHub {
	private final CarDAO carDAO;
	private final SpareItemDAO spareItemDAO;
	private final ItemDAO itemDAO;
	private final UserDAO userDAO;
	private final UserOrderDAO userOrderDAO;
	private final UserStatsDAO userStatsDAO;
	private final UserRewardsDAO userRewardsDAO;


	public CarHub(CarDAO carDAO, SpareItemDAO spareItemDAO, ItemDAO itemDAO,
				  UserDAO userDAO, UserOrderDAO userOrderDAO,
				  UserStatsDAO userStatsDAO, UserRewardsDAO userRewardsDAO) {
		this.carDAO = carDAO;
		this.spareItemDAO = spareItemDAO;
		this.itemDAO = itemDAO;
		this.userDAO = userDAO;
		this.userOrderDAO = userOrderDAO;
		this.userStatsDAO = userStatsDAO;
		this.userRewardsDAO = userRewardsDAO;
	}


	public List<Car> getAllCars(String userName) {
		List<Car> allCars = new ArrayList<>();

		List<Integer> allCarUid = carDAO.getAllCarUid(userName);
		for (Integer curCarUid : allCarUid) {
			Car curCar = carDAO.getCar(userName, curCarUid);
			allCars.add(curCar);
		}

		return allCars;
	}

	// returns new carUid
	public int constructCar(String userName, String newCarName, List<Integer> itemUids) {
		if (userDAO.getUser(userName) == null) return Car.NONE_UID;
		if (newCarName == null) return Car.NONE_UID;
		if (itemUids.isEmpty()) return Car.NONE_UID;

		int newCarUid = carDAO.declare(userName, newCarName);
		if (newCarUid == Car.NONE_UID) return Car.NONE_UID;

		for (Integer curItemUid : itemUids) {
			carDAO.putItem(userName, newCarUid, curItemUid);
		}

		return newCarUid;
	}

	public boolean disassembleCar(String userName, int carUid) {
		if (userDAO.hasCar(userName)) return false;

		List<Integer> allItemUid = carDAO.getAllItemUid(userName, carUid);
		if (allItemUid == null || allItemUid.isEmpty()) return false;

		for (Integer curItemUid : allItemUid) {
			carDAO.takeItem(userName, carUid, curItemUid);
		}

		return true;
	}



}
