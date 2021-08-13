package com.machinarium.model.Item;

import com.machinarium.model.globals.ID;

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


	public Item() { //todo remove
		this(null, null);
	}


	public ID getID() {
		return iD;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return "Item";
	}

	public List<String> getSpecs() {
		List<String> specs = new ArrayList<>();
		String spec = "[name: " + name + "]";
		specs.add(spec);
		return specs;
	}

	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = new HashMap<>();
		specs.put("name", name);
		return specs;
	}

	@Override
	public String toString() {
		String str = "";
		str += "[iD: " + iD + "] \n";
		str += "[name: " + name + "] \n";
		return str;
	}

}
