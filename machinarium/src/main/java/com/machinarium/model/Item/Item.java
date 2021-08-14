package com.machinarium.model.Item;

import com.machinarium.utility.common.ID;
import com.machinarium.utility.constants.ItemConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item {
	private final ID id;
	private final String name;


	public Item(ID id, String name) {
		this.id = id;
		this.name = name;
	}


	public ID getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return ItemConstants.ITEM_TYPE;
	}

	public List<String> getSpecs() {
		List<String> specs = new ArrayList<>();
		String spec = "[" + ItemConstants.NAME + ": " + getName() + "]";
		specs.add(spec);
		return specs;
	}

	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = new HashMap<>();
		specs.put(ItemConstants.NAME, getName());
		return specs;
	}

	@Override
	public String toString() {
		String str = "";
		str += "[" + ItemConstants.ID + ": " + getID() + "] \n";
		str += "[" + ItemConstants.NAME + ": " + getName() + "] \n";
		return str;
	}

	public Map<String, String> toMap() {

		return Map.of(ItemConstants.ID, getID().toString(),
				      ItemConstants.NAME, getName());
	}
}
