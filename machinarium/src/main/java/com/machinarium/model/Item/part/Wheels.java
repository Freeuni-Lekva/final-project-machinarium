package com.machinarium.model.Item.part;

public class Wheels extends Part {
	private final int tractionUnit;

	public Wheels(int uid, String nameID, int weight, int tractionUnit) {
		super(uid, nameID, weight);
		this.tractionUnit = tractionUnit;
	}

	public int getTractionUnit() {
		return tractionUnit;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[tractionUnit: " + tractionUnit + "] \n";
		return str;
	}

}
