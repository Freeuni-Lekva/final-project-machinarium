package com.machinarium.dao;

import com.machinarium.model.userBin.UserOrder;

public interface OrderDAO {
	boolean addOrder(UserOrder userOrder);
}
