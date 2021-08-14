package com.machinarium.model.Item.connector;

import com.machinarium.model.Item.Item;
import com.machinarium.model.Item.part.Part;
import com.machinarium.utility.common.ID;
import com.machinarium.utility.constants.ItemConstants;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Connector<PartA, PartB> extends Item {
	private final Part partA;
	private final Part partB;


	public Connector(ID iD, String name, Part partA, Part partB) {
		super(iD, name);
		this.partA = Objects.requireNonNull(partA);
		this.partB = Objects.requireNonNull(partB);
	}


	@Override
	public String getType() {
		return ItemConstants.CONNECTOR_TYPE;
	}

	@Override
	public List<String> getSpecs() {
		List<String> specs = super.getSpecs();
		String spec = "[" + ItemConstants.CONNECTOR_TYPE + ": <" + partA.getType() + ", " +
				                                             partB.getType() + ">]";
		specs.add(spec);
		return specs;
	}

	@Override
	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = super.getMappedSpecs();
		String spec = "<" + partA.getType() + ", " + partB.getType() + ">";
		specs.put(ItemConstants.CONNECTOR_TYPE, spec);
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[" + ItemConstants.CONNECTOR_TYPE + ": <" + partA.getType() + ", " +
				                                      partB.getType() + ">] \n";
		return str;
	}

}
