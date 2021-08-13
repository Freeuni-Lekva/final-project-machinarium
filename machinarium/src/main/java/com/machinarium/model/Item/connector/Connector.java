package com.machinarium.model.Item.connector;

import com.machinarium.model.Item.Item;
import com.machinarium.model.Item.part.Part;
import com.machinarium.model.globals.ID;
import com.machinarium.model.globals.ItemLib;

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

//	public Connector() { //todo remove
//		super();
//		this.partA = null;
//		this.partB = null;
//	}


	@Override
	public String getType() {
		return ItemLib.CONNECTOR_TYPE;
	}

	@Override
	public List<String> getSpecs() {
		List<String> specs = super.getSpecs();
		String spec = "[" + ItemLib.CONNECTOR_TYPE + ": <" + partA.getType() + ", " +
				                                             partB.getType() + ">]";
		specs.add(spec);
		return specs;
	}

	@Override
	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = super.getMappedSpecs();
		String spec = "<" + partA.getType() + ", " + partB.getType() + ">";
		specs.put(ItemLib.CONNECTOR_TYPE, spec);
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[" + ItemLib.CONNECTOR_TYPE + ": <" + partA.getType() + ", " +
				                                      partB.getType() + ">] \n";
		return str;
	}

}
