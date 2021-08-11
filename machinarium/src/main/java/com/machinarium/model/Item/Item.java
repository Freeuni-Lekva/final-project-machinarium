package com.machinarium.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item {
	public static final int NONE_UID = -1;
	public static final String NONE_NAME = "";

	private final int uid;
	private final String name;


	public Item(int uid, String name) {
		this.uid = uid;
		this.name = name;
	}

	public Item() {
		this(NONE_UID, NONE_NAME);
	}


	public int getUid() {
		return uid;
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
		str += "[uid: " + uid + "] \n";
		str += "[name: " + name + "] \n";
		return str;
	}

}
