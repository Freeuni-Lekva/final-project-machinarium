package com.machinarium.model.Item.part;

import com.machinarium.model.Item.Item;
import com.machinarium.utility.common.ID;
import com.machinarium.utility.constants.ItemConstants;
import java.util.List;
import java.util.Map;

public abstract class Part extends Item {
	private final Integer weight;


	public Part(ID iD, String name, Integer weight) {
		super(iD, name);
		this.weight = weight;
	}


	public Integer getWeight() {
		return weight;
	}

	@Override
	public String getType() {
		return ItemConstants.PART_TYPE;
	}

	@Override
	public List<String> getSpecs() {
		List<String> specs = super.getSpecs();
		String spec = "[" + ItemConstants.WEIGHT + ": " + weight + "]";
		specs.add(spec);
		return specs;
	}

	@Override
	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = super.getMappedSpecs();
		specs.put(ItemConstants.WEIGHT, weight.toString());
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[" + ItemConstants.WEIGHT + ": " + weight + "] \n";
		return str;
	}

}
