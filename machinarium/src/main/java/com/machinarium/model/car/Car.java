package com.machinarium.model.car;

public abstract class Car {
	public static final double TIME_NA = -1.0;
	public static final int TU_TO_HP = 100;
	public static final int AD_TO_HP = 20;

	private final String name;
	private final String nameID;

	public Car(String name, String nameID) {
		this.name = name;
		this.nameID = nameID;
	}


	public String getName() {
		return name;
	}

	public String getNameID() {
		return nameID;
	}


	public abstract boolean isValid();

	public abstract double quarterMileTime();


	@Override
	public String toString() {
		String str = "";
		str += "[name: " + name + "] \n";
		str += "[nameID: " + nameID + "] \n";
		return str;
	}

}
