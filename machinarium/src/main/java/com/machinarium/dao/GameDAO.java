package com.machinarium.dao;

import com.machinarium.model.car.Car;
import com.machinarium.utility.common.ID;

import java.util.List;

public interface GameDAO {
	String getGameHost(ID gameID);
	List<String> getGameUsers(ID gameID);

	String getGameStage(ID gameID);
	Car getUserChosenCar(String userName, ID gameID);

}
