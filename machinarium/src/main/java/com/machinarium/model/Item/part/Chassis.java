package com.machinarium.model.Item.part;

import com.machinarium.model.globals.ID;
import com.machinarium.model.globals.ItemLib;

import java.util.List;
import java.util.Map;

public class Chassis extends Part {
	private final int weightSupport;


	public Chassis(ID iD, String name, int weight, int weightSupport) {
		super(iD, name, weight);
		this.weightSupport = weightSupport;
	}


	public int getWeightSupport() {
		return weightSupport;
	}

	@Override
	public String getType() {
		return ItemLib.CHASSIS_TYPE;
	}

	@Override
	public List<String> getSpecs() {
		List<String> specs = super.getSpecs();
		String spec = "[" + ItemLib.WEIGHT_SUPPORT + ": " + weightSupport + "]";
		specs.add(spec);
		return specs;
	}

	@Override
	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = super.getMappedSpecs();
		specs.put(ItemLib.WEIGHT_SUPPORT, weightSupport + "");
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[" + ItemLib.WEIGHT_SUPPORT + ": " + weightSupport + "] \n";
		return str;
	}

}
