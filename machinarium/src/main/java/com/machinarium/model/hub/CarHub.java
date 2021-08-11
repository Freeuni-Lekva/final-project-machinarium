package com.machinarium.model.hub;

import com.machinarium.dao.CarDAO;
import com.machinarium.dao.SpareItemDAO;
import com.machinarium.dao.UserDAO;
import com.machinarium.model.car.Car;

import java.util.ArrayList;
import java.util.List;

public class CarHub {
	private UserDAO userDAO;
	private SpareItemDAO spareItemDAO;
	private CarDAO carDAO;

	public CarHub(UserDAO userDAO, SpareItemDAO spareItemDAO, CarDAO carDAO) {
		this.userDAO = userDAO;
		this.spareItemDAO = spareItemDAO;
		this.carDAO = carDAO;
	}

	public List<Car> getCars(String userName) {
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
		if (carDAO.hasNoCar(userName)) return false;

		List<Integer> allItemUid = carDAO.getAllItemUid(userName, carUid);
		if (allItemUid == null || allItemUid.isEmpty()) return false;

		for (Integer curItemUid : allItemUid) {
			carDAO.takeItem(userName, carUid, curItemUid);
		}

		return true;
	}



}
