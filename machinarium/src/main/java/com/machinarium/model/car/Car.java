package com.machinarium.model.car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Car {
	public static final int NONE_UID = -1;
	public static final double TIME_NA = -1.0;
	public static final int TU_TO_HP = 100;
	public static final int AD_TO_HP = 20;

	private final int uid;
	private final String name;

	public Car(int uid, String name) {
		this.uid = uid;
		this.name = name;
	}


	public int getUid() {
		return uid;
	}

	public String getName() {
		return name;
	}


	public abstract boolean isValid();

	public abstract double quarterMileTime();


	public String getType() {
		return "Car";
	}

	public List<List<String>> getSpecs() {
		List<List<String>> fullSpecs = new ArrayList<>();

		List<String> specs = new ArrayList<>();
		String spec = "[name: " + name + "]";
		specs.add(spec);

		fullSpecs.add(specs);
		return fullSpecs;
	}

	public Map<String, Map<String, String>> getMappedSpecs() {
		Map<String,  Map<String, String>> fullSpecs = new HashMap<>();

		Map<String, String> specs = new HashMap<>();
		specs.put("name", name);

		fullSpecs.put("String", specs);
		return fullSpecs;
	}

	@Override
	public String toString() {
		String str = "";
		str += "[uid: " + uid + "] \n";
		str += "[name: " + name + "] \n";
		return str;
	}

}
