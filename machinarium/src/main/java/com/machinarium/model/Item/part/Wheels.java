package com.machinarium.model.Item.part;

import java.util.List;

public class Wheels extends Part {
	public static final int NONE_TRACTION_UNIT = -1;

	private final int tractionUnit;

	public Wheels(int uid, String nameID, int weight, int tractionUnit) {
		super(uid, nameID, weight);
		this.tractionUnit = tractionUnit;
	}

	public Wheels() {
		super();
		this.tractionUnit = NONE_TRACTION_UNIT;
	}


	public int getTractionUnit() {
		return tractionUnit;
	}

	@Override
	public String getType() {
		return "Wheels";
	}

	@Override
	public List<String> getSpecs() {
		List<String> specs = super.getSpecs();
		String spec = "[tractionUnit: " + tractionUnit + "]";
		specs.add(spec);
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[tractionUnit: " + tractionUnit + "] \n";
		return str;
	}

}
