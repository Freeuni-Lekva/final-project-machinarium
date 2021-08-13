package com.machinarium.model.Item.part;

import com.machinarium.model.globals.ID;

import java.util.List;
import java.util.Map;

public class Chassis extends Part {
	private final int weightSupport;


	public Chassis(ID iD, String name, int weight, int weightSupport) {
		super(iD, name, weight);
		this.weightSupport = weightSupport;
	}

	public Chassis() { //todo remove
		super();
		this.weightSupport = -1;
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
	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = super.getMappedSpecs();
		specs.put("weightSupport", weightSupport + "");
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[weightSupport: " + weightSupport + "] \n";
		return str;
	}

}
