package com.machinarium.model.user;

import com.machinarium.model.Item.Item;
import com.machinarium.utility.common.ID;

import java.util.Map;

public class Order {

	private final String userName;
	private final ID id;

	private final String status;
	private final String date;

	private final Map<Item, Integer> userGives;
	private final Map<Item, Integer> userTakes;


	public Order(String userName, ID id, String status, String date,
				 Map<Item, Integer> userGives, Map<Item, Integer> userTakes) {
		this.userName = userName;
		this.id = id;

		this.status = status;
		this.date = date;

		this.userGives = userGives;
		this.userTakes = userTakes;
	}


	public String getUserName() {
		return userName;
	}

	public ID getID() {
		return id;
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
