package com.machinarium.model.Item.part;

import com.machinarium.model.Item.Item;
import com.machinarium.model.globals.ID;
import com.machinarium.model.globals.ItemLib;

import java.util.List;
import java.util.Map;

public abstract class Part extends Item {
	private final int weight;


	public Part(ID iD, String name, int weight) {
		super(iD, name);
		this.weight = weight;
	}

	public Part() { //todo remove
		super();
		this.weight = -1;
	}


	public int getWeight() {
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
		specs.put(ItemLib.WEIGHT, weight + "");
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[" + ItemLib.WEIGHT + ": " + weight + "] \n";
		return str;
	}

}
