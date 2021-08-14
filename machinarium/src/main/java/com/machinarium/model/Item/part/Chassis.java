package com.machinarium.model.Item.part;

import com.machinarium.utility.common.ID;
import com.machinarium.utility.constants.ItemConstants;
import java.util.List;
import java.util.Map;

public class Chassis extends Part {
	private final Integer weightSupport;


	public Chassis(ID iD, String name, Integer weight, Integer weightSupport) {
		super(iD, name, weight);
		this.weightSupport = weightSupport;
	}


	public Integer getWeightSupport() {
		return weightSupport;
	}

	@Override
	public String getType() {
		return ItemConstants.CHASSIS_TYPE;
	}

	@Override
	public List<String> getSpecs() {
		List<String> specs = super.getSpecs();
		String spec = "[" + ItemConstants.WEIGHT_SUPPORT + ": " + weightSupport + "]";
		specs.add(spec);
		return specs;
	}

	@Override
	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = super.getMappedSpecs();
		specs.put(ItemConstants.WEIGHT_SUPPORT, weightSupport.toString());
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[" + ItemConstants.WEIGHT_SUPPORT + ": " + weightSupport + "] \n";
		return str;
	}

}
