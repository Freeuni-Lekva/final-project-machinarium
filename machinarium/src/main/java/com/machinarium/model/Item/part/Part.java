package com.machinarium.model.Item.part;

import com.machinarium.model.Item.Item;
import com.machinarium.model.globals.ID;
import com.machinarium.model.globals.ItemLib;
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
		return ItemLib.PART_TYPE;
	}

	@Override
	public List<String> getSpecs() {
		List<String> specs = super.getSpecs();
		String spec = "[" + ItemLib.WEIGHT + ": " + weight + "]";
		specs.add(spec);
		return specs;
	}

	@Override
	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = super.getMappedSpecs();
		specs.put(ItemLib.WEIGHT, weight.toString());
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[" + ItemLib.WEIGHT + ": " + weight + "] \n";
		return str;
	}

}
