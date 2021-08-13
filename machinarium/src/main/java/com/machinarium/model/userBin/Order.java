package com.machinarium.model.userBin;

import com.machinarium.model.Item.Item;

import java.util.Map;

public class Order {
	private final String userName;
	private final int ID;

	private final String status;
	private final String date;

	private final Map<Item, Integer> userGives;
	private final Map<Item, Integer> userTakes;


	public Order(String userName, int ID, String status, String date,
				 Map<Item, Integer> userGives, Map<Item, Integer> userTakes) {
		this.userName = userName;
		this.ID = ID;

		this.status = status;
		this.date = date;

		this.userGives = userGives;
		this.userTakes = userTakes;
	}


	public String getUserName() {
		return userName;
	}

	public int getID() {
		return ID;
	}

	public String getStatus() {
		return status;
	}

	public String getDate() {
		return date;
	}

	public Map<Item, Integer> getUserGives() {
		return userGives;
	}

	public Map<Item, Integer> getUserTakes() {
		return userTakes;
	}

}
