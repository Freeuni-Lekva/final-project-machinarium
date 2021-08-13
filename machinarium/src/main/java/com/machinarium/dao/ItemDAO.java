package com.machinarium.dao;

import com.machinarium.model.Item.Item;
import com.machinarium.model.globals.ID;

public interface ItemDAO {
	Item getItem(ID itemID);

}
