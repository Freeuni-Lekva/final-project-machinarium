package com.machinarium.model.car;

public interface Car {
	String getName();
	void setName(String name);

	String getNameID();
	void setNameID(String nameID);

	boolean isValid();

	double quarterMileTime();
}
