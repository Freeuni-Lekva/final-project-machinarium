package com.machinarium.model.Item.part;

import com.machinarium.model.globals.ID;
import com.machinarium.model.globals.ItemLib;

import java.util.List;
import java.util.Map;

public class Engine extends Part {
	private final Integer horsePower;


	public Engine(ID iD, String name, Integer weight, Integer horsePower) {
		super(iD, name, weight);
		this.horsePower = horsePower;
	}


	public Integer getHorsePower() {
		return horsePower;
	}

	@Override
	public String getType() {
		return ItemLib.ENGINE_TYPE;
	}

	@Override
	public List<String> getSpecs() {
		List<String> specs = super.getSpecs();
		String spec = "[" + ItemLib.HORSE_POWER + ": " + horsePower + "]";
		specs.add(spec);
		return specs;
	}

	@Override
	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = super.getMappedSpecs();
		specs.put(ItemLib.HORSE_POWER, horsePower.toString());
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[" + ItemLib.HORSE_POWER + ": " + horsePower + "] \n";
		return str;
	}

}