package com.machinarium.model.Item.part;

import java.util.List;

public class Chassis extends Part {
	public static final int NONE_WEIGHT_SUPPORT = -1;

	private final int weightSupport;

	public Chassis(int uid, String name, int weight, int weightSupport) {
		super(uid, name, weight);
		this.weightSupport = weightSupport;
	}

	public Chassis() {
		super();
		this.weightSupport = NONE_WEIGHT_SUPPORT;
	}


	public int getWeightSupport() {
		return weightSupport;
	}

	@Override
	public String getType() {
		return "Chassis";
	}

	@Override
	public List<String> getSpecs() {
		List<String> specs = super.getSpecs();
		String spec = "[weightSupport: " + weightSupport + "]";
		specs.add(spec);
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[weightSupport: " + weightSupport + "] \n";
		return str;
	}

}
