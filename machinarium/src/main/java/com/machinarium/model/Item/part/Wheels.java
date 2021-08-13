package com.machinarium.model.Item.part;

import com.machinarium.model.globals.ID;

import java.util.List;
import java.util.Map;

public class Wheels extends Part {
	private final int tractionUnit;


	public Wheels(ID iD, String nameID, int weight, int tractionUnit) {
		super(iD, nameID, weight);
		this.tractionUnit = tractionUnit;
	}

	public Wheels() { //todo remove
		super();
		this.tractionUnit = -1;
	}


	public int getTractionUnit() {
		return tractionUnit;
	}

	@Override
	public String getType() {
		return "Wheels";
	}

	@Override
	public List<String> getSpecs() {
		List<String> specs = super.getSpecs();
		String spec = "[tractionUnit: " + tractionUnit + "]";
		specs.add(spec);
		return specs;
	}

	@Override
	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = super.getMappedSpecs();
		specs.put("tractionUnit", tractionUnit + "");
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[tractionUnit: " + tractionUnit + "] \n";
		return str;
	}

}
