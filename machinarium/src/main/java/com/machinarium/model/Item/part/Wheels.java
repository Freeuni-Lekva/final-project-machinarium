package com.machinarium.model.Item.part;

import com.machinarium.utility.common.ID;
import com.machinarium.utility.constants.ItemConstants;

import java.util.List;
import java.util.Map;

public class Wheels extends Part {
	private final Integer tractionUnit;


	public Wheels(ID iD, String nameID, Integer weight, Integer tractionUnit) {
		super(iD, nameID, weight);
		this.tractionUnit = tractionUnit;
	}


	public Integer getTractionUnit() {
		return tractionUnit;
	}

	@Override
	public String getType() {
		return ItemConstants.WHEELS_TYPE;
	}

	@Override
	public List<String> getSpecs() {
		List<String> specs = super.getSpecs();
		String spec = "[" + ItemConstants.TRACTION_UNIT + ": " + tractionUnit + "]";
		specs.add(spec);
		return specs;
	}

	@Override
	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = super.getMappedSpecs();
		specs.put(ItemConstants.TRACTION_UNIT, tractionUnit.toString());
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[" + ItemConstants.TRACTION_UNIT + ": " + tractionUnit + "] \n";
		return str;
	}

}
