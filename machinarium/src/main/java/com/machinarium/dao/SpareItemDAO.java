package com.machinarium.dao;

import com.machinarium.model.Item.Item;

import java.util.List;

public interface SpareItemDAO {

	boolean hasSpareItem(String userName);
	int spareItemCount(String userName);

	boolean hasItem(String userName, int itemID);

	boolean addItem(String userName, int itemID);
	boolean removeItem(String userName, int itemID);

	List<Integer> getAllSpareItemID(String userName);
	List<Item> getAllSpareItem(String userName); //++ //ToDo: Review

}
