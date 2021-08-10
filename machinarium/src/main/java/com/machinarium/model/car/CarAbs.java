package com.machinarium.model.car;

public class CarAbs implements Car {
	public static final double TIME_NA = -1.0;
	public static final int TU_TO_HP = 100;
	public static final int AD_TO_HP = 20;

	private final String name;
	private final String nameID;

	public CarAbs(String name, String nameID) {
		this.name = name;
		this.nameID = nameID;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getNameID() {
		return nameID;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public double quarterMileTime() {
		return TIME_NA;
	}

}
