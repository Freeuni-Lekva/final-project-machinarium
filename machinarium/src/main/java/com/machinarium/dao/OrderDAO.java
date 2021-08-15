package com.machinarium.dao;

import com.machinarium.model.user.Order;
import com.machinarium.utility.common.ID;

import java.util.List;
import java.util.Map;

public interface OrderDAO {

	public static final String ACTIVE = "Active";

	Order getOrder(String userName, ID orderID);

	List<Order> getAllOrders(String userName);

	ID addOrder(String userName, Map<ID, Integer> userGives, Map<ID, Integer> userTakes); // returns ID

	/**
	 * Updates the status of the specified order.
	 *
	 * @param orderID The {@link ID} of the order.
	 * @param status The new status as a {@link String}.
	 * @return True if the update was successful.
	 */
	boolean updateOrderStatus(ID orderID, String status);

	/**
	 * Cancels all orders of the speciifed user.
	 *
	 * @param userName The username of the user.
	 * @return True if the update was successful.
	 */
	boolean cancelAllOrders(String userName);

	/**
	 * Cancels the specified order.
	 *
	 * @param orderID The id of the order.
	 * @return True if the order was successfully canceled.
	 */
	boolean cancelOrder(ID orderID);
}
