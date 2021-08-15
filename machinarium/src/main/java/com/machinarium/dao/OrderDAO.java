package com.machinarium.dao;

import com.machinarium.model.Item.Item;
import com.machinarium.utility.common.ID;
import com.machinarium.model.history.Order;
import java.util.List;
import java.util.Map;

public interface OrderDAO {
	Order getOrder(String userName, ID orderID);
	List<Order> getAllOrders(String userName);

	ID addOrder(String userName, Map<ID, Integer> userGives,
								 Map<ID, Integer> userTakes); // returns ID
	boolean removeOrder(String userName, ID orderID);

}
