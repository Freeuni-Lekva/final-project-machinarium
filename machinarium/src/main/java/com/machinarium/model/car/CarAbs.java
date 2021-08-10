package com.machinarium.model.car;

public class CarAbs implements Car {
	public static final double TIME_NA = -1.0;
	public static final int TU_TO_HP = 100;
	public static final int AD_TO_HP = 20;

	private String name;
	private String nameID;


	public CarAbs(String name, String nameID) {
		this.name = name;
		this.nameID = nameID;
	}

	public CarAbs() {
		this(null, null);
	}


	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String getNameID() {
		return nameID;
	}

	@Override
	public void setNameID(String nameID) {
		this.nameID = nameID;
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
