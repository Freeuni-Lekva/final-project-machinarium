package com.machinarium.model.Item.part;

public class Chassis extends Part {
	private final int weightSupport;

	public Chassis(int uid, String name, int weight, int weightSupport) {
		super(uid, name, weight);
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
