package com.machinarium.dao;

import com.machinarium.model.userBin.UserOrder;

public interface OrderDAO {
	UserOrder getOrders(String userName);
	boolean addOrder(UserOrder userOrder);
}
