package com.machinarium.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item {
	public static final int NONE_ID = -1;
	public static final String NONE_NAME = "";

	private final int ID;
	private final String name;


	public Item(int ID, String name) {
		this.ID = ID;
		this.name = name;
	}

	public Item() {
		this(NONE_ID, NONE_NAME);
	}


	public int getID() {
		return ID;
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
		str += "[ID: " + ID + "] \n";
		str += "[name: " + name + "] \n";
		return str;
	}

}
