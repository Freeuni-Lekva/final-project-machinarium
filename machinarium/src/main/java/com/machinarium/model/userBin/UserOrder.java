package com.machinarium.model.userBin;

import com.machinarium.model.Item.Item;

import java.util.Map;

public class UserOrder {
	private final String userName;
	private final Map<Item, Integer> userGives;
	private final Map<Item, Integer> userTakes;
	private final String orderDate;

	public UserOrder(String userName, Map<Item, Integer> userGives, Map<Item, Integer> userTakes, String orderDate) {
		this.userName = userName;
		this.userGives = userGives;
		this.userTakes = userTakes;
		this.orderDate = orderDate;
	}

	public String getUserName() {
		return userName;
	}

	public Map<Item, Integer> getUserGives() {
		return userGives;
	}

	public Map<Item, Integer> getUserTakes() {
		return userTakes;
	}

	public String getOrderDate() {
		return orderDate;
	}

}
