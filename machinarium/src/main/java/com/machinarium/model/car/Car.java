package com.machinarium.model.car;

import com.machinarium.model.Item.Item;
import com.machinarium.utility.common.ID;
import com.machinarium.utility.constants.CarConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Car {
	public static final double TIME_NA = -1.0;
	public static final int TU_TO_HP = 100;
	public static final int AD_TO_HP = 20;

	private final ID id;
	private final String name;

	public Car(ID id, String name) {
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
		return CarConstants.CAR_TYPE;
	}

	public abstract List<Item> getComponents();


	public abstract boolean isValid();

	public abstract double quarterMileTime();


	public List<List<String>> getSpecs() {

		List<List<String>> specifications = getComponents().stream().map(Item::getSpecs).collect(Collectors.toList());
		specifications.add(List.of("[" + CarConstants.NAME + ": " + getName() + "]"));

		return specifications;
	}

	public Map<String, Map<String, String>> getMappedSpecs() {

		Map<String,  Map<String, String>> specifications = new HashMap<>();

		specifications.put(CarConstants.NAME, Map.of(CarConstants.NAME, getName()));
		getComponents().stream().forEach(item -> {specifications.put(item.getType(), item.getMappedSpecs());});

		return specifications;
	}

	@Override
	public String toString() {

		StringBuilder asString = new StringBuilder();
		asString.append("[" + CarConstants.ID + ": " + getID() + "] \n");
		asString.append("[" + CarConstants.NAME + ": " + getName() + "] \n");

		getComponents().forEach(item -> {
			asString.append("[" + item.getType() + ": \n" + item + "\n] \n");
		});

		return asString.toString();
	}

	public Map<String, Object> toMap() {

		Map<String, Object> map = new HashMap<>();

		map.put(CarConstants.ID, getID().toString());
		map.put(CarConstants.NAME, getName());
		map.put(CarConstants.COMPONENTS, getComponents().stream().map(Item::toMap).collect(Collectors.toList()));

		return map;
	}
}
