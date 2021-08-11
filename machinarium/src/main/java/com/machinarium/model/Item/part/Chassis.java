package com.machinarium.model.Item.part;

public class Chassis extends CarPart {
	private int weightSupport;

	public Chassis(String name, String nameID, int weight, int weightSupport) {
		super(name, nameID, weight);
		this.weightSupport = weightSupport;
	}


	public int getWeightSupport() {
		return weightSupport;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[weightSupport: " + weightSupport + "] \n";
		return str;
	}

}
