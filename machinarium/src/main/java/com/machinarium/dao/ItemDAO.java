package com.machinarium.dao;

import com.machinarium.model.Item.Item;

public interface ItemDAO {
	boolean itemExists(int itemUid);
	Item getItem(int itemUid);
}
