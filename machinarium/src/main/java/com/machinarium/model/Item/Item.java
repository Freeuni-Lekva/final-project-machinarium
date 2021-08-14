package com.machinarium.model.Item;

import com.machinarium.utility.common.ID;
import com.machinarium.utility.constants.ItemConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item {
	private final ID iD;
	private final String name;


	public Item(ID iD, String name) {
		this.iD = iD;
		this.name = name;
	}


	public ID getID() {
		return iD;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return ItemConstants.ITEM_TYPE;
	}

	public List<String> getSpecs() {
		List<String> specs = new ArrayList<>();
		String spec = "[" + ItemConstants.NAME + ": " + name + "]";
		specs.add(spec);
		return specs;
	}

	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = new HashMap<>();
		specs.put(ItemConstants.NAME, name);
		return specs;
	}

	@Override
	public String toString() {
		String str = "";
		str += "[" + ItemConstants.ITEM_ID + ": " + iD + "] \n";
		str += "[" + ItemConstants.NAME + ": " + name + "] \n";
		return str;
	}

}
