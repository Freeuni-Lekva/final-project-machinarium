package com.machinarium.model.car;

import com.machinarium.model.globals.CarLib;
import com.machinarium.model.globals.ID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Car {
	public static final double TIME_NA = -1.0;
	public static final int TU_TO_HP = 100;
	public static final int AD_TO_HP = 20;

	private final ID iD;
	private final String name;

	public Car(ID iD, String name) {
		this.iD = iD;
		this.name = name;
	}


	public ID getID() {
		return iD;
	}

	public String getName() {
		return name;
	}


	public abstract boolean isValid();

	public abstract double quarterMileTime();


	public String getType() {
		return CarLib.CAR_TYPE;
	}

	public List<List<String>> getSpecs() {
		List<List<String>> fullSpecs = new ArrayList<>();

		List<String> specs = new ArrayList<>();
		String spec = "[" + CarLib.NAME + ": " + name + "]";
		specs.add(spec);

		fullSpecs.add(specs);
		return fullSpecs;
	}

	public Map<String, Map<String, String>> getMappedSpecs() {
		Map<String,  Map<String, String>> fullSpecs = new HashMap<>();

		Map<String, String> specs = new HashMap<>();
		specs.put(CarLib.NAME, name);

		fullSpecs.put(CarLib.NAME, specs);
		return fullSpecs;
	}

	@Override
	public String toString() {
		String str = "";
		str += "[" + CarLib.ITEM_ID + ": " + iD + "] \n";
		str += "[" + CarLib.NAME + ": " + name + "] \n";
		return str;
	}

}