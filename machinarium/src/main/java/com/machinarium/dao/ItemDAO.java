package com.machinarium.dao;

import com.machinarium.model.Item.Item;

public interface ItemDAO {

	boolean hasNoSpareItem(String userName);
	int spareItemCount(String userName);

	boolean hasItem(String userName, int itemUid);

	boolean addItem(String userName, int itemUid);
	boolean removeItem(String userName, int itemUid);

}
