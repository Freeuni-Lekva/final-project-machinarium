package com.machinarium.dao;

import com.machinarium.model.Item.Item;
import com.machinarium.model.userBin.UserOrder;

import java.util.List;
import java.util.Map;

public interface OrderDAO {
	UserOrder getOrder(String userName, int orderID);
	List<UserOrder> getAllOrders(String userName);

	int addOrder(String userName, Map<Item, Integer> userGives, Map<Item, Integer> userTakes); // returns ID
	boolean removeOrder(String userName, int orderID);
}
