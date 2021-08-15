package com.machinarium.model.Item;

import com.machinarium.model.JSONData;
import com.machinarium.utility.common.ID;
import com.machinarium.utility.constants.ItemConstants;
import com.machinarium.utility.constants.ServletConstants;

import java.util.*;

public class Item implements JSONData {
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

	@Override
	public Map<String, Object> toJSONMap() {

		return Map.of(ItemConstants.JSON_ID, getID().toString(),
					  ItemConstants.JSON_NAME, getName(),
					  ItemConstants.JSON_TYPE, getType());
	}

	public Map<String, Object> toJSONMap(int amount) {

		Map<String, Object> jsonData = new HashMap<>(toJSONMap());
		jsonData.put(ItemConstants.JSON_AMOUNT, amount);

		return Collections.unmodifiableMap(jsonData);
	}
}
