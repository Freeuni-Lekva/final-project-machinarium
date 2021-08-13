package com.machinarium.model.history;

import com.machinarium.model.Item.Item;
import com.machinarium.model.globals.ID;

import java.util.Map;

public class Order {
	private final String userName;
	private final ID iD;

	private final String status;
	private final String date;

	private final Map<Item, Integer> userGives;
	private final Map<Item, Integer> userTakes;


	public Order(String userName, ID iD, String status, String date,
				 Map<Item, Integer> userGives, Map<Item, Integer> userTakes) {
		this.userName = userName;
		this.iD = iD;

		this.status = status;
		this.date = date;

		this.userGives = userGives;
		this.userTakes = userTakes;
	}


	public String getUserName() {
		return userName;
	}

	public ID getID() {
		return iD;
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
