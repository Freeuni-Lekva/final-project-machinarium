package com.machinarium.dao;

import com.machinarium.model.Item.Item;
import com.machinarium.utility.common.ID;

import java.util.List;

public interface ItemDAO {

	Item getItem(ID itemID);

	/**
	 * Returns a list of all available items from the database.
	 *
	 * @return The list of items as a {@link List<Item>} object.
	 */
	List<Item> getAllItems();
}
