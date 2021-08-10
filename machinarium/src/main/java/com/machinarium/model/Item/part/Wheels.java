package com.machinarium.model.Item.part;

public class Wheels extends CarPart {
	private int tractionUnit;

	public Wheels(String name, String nameID, int weight, int tractionUnit) {
		super(name, nameID, weight);
		this.tractionUnit = tractionUnit;
	}

	@Override
	public String toString() {
		String str = super.toString() + ", ";
		str += "[tractionUnit: " + tractionUnit + "]";
		return str;
	}

}
