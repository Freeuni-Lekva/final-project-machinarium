package com.machinarium.dao;

import com.machinarium.model.Item.Item;
import com.machinarium.model.userBin.Order;

import java.util.List;
import java.util.Map;

public interface OrderDAO {
	Order getOrder(String userName, int orderID);
	List<Order> getAllOrders(String userName);

	int addOrder(String userName, Map<Item, Integer> userGives,
				                  Map<Item, Integer> userTakes); // returns ID
	boolean removeOrder(String userName, int orderID);

}
