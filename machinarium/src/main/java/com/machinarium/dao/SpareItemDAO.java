package com.machinarium.dao;

import java.util.List;

public interface SpareItemDAO {

	boolean hasNoSpareItem(String userName);
	int spareItemCount(String userName);

	boolean hasItem(String userName, int itemID);

	boolean addItem(String userName, int itemID);
	boolean removeItem(String userName, int itemID);

	List<Integer> getAllSpareItemID(String userName);

}
