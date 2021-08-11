package com.machinarium.model.car;

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


	@Override
	public String toString() {
		String str = "";
		str += "[uid: " + uid + "] \n";
		str += "[name: " + name + "] \n";
		return str;
	}

}
