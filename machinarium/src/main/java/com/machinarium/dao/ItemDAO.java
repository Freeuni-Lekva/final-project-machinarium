package com.machinarium.dao;

import com.machinarium.model.Item.Item;

public interface ItemDAO {
	void makeOrder(String userName, Item item); //ToDo: remove
	void exchange(String userNameA, String userNameB, Item itemA, Item itemB); //ToDo: remove


}
