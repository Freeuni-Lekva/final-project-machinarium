package com.machinarium.dao;

import com.machinarium.model.Item.Item;

public interface ItemDAO {
	void makeOrder(User user, Item item);
	void exchange(User userA, User userB, Item itemA, Item itemB);
}
