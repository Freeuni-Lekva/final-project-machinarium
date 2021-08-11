package com.machinarium.dao;

import com.machinarium.model.Item.Item;

public interface ItemDAO {

	boolean hasNoSpareItem(String userName);
	int spareItemCount(String userName);

	boolean hasItem(String userName, String itemNameID);

	boolean addItem(String userName, String itemNameID);
	boolean removeItem(String userName, String itemNameID);

}
