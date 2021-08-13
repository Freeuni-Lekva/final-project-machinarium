package com.machinarium.model.Item;

import com.machinarium.model.globals.ID;
import com.machinarium.model.globals.ItemLib;
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
		return ItemLib.ITEM_TYPE;
	}

	public List<String> getSpecs() {
		List<String> specs = new ArrayList<>();
		String spec = "[" + ItemLib.NAME + ": " + name + "]";
		specs.add(spec);
		return specs;
	}

	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = new HashMap<>();
		specs.put(ItemLib.NAME, name);
		return specs;
	}

	@Override
	public String toString() {
		String str = "";
		str += "[" + ItemLib.ITEM_ID + ": " + iD + "] \n";
		str += "[" + ItemLib.NAME + ": " + name + "] \n";
		return str;
	}

}
