package com.machinarium.model.Item.part;

public class Chassis extends CarPart {
	private int weightSupport;

	public Chassis(String name, String nameID, int weight, int weightSupport) {
		super(name, nameID, weight);
		this.weightSupport = weightSupport;
	}

	@Override
	public String toString() {
		String str = super.toString() + ", ";
		str += "[weightSupport: " + weightSupport + "]";
		return str;
	}

	public int getWeightSupport() {
		return weightSupport;
	}

}
