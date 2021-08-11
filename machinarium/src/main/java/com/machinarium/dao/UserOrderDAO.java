package com.machinarium.dao;

import com.machinarium.model.userBin.UserOrder;

public interface UserOrderDAO {
	boolean makeOrder(UserOrder userOrder);
}
