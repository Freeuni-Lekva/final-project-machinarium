package com.machinarium.model.Item.connector;

import com.machinarium.model.Item.Item;
import com.machinarium.model.Item.part.Part;

import java.util.List;

public class Connector<PartA, PartB> extends Item {
	private final Part partA;
	private final Part partB;

	public Connector(int uid, String name, Part partA, Part partB) {
		super(uid, name);
		this.partA = partA;
		this.partB = partB;
	}

	public Connector() {
		super();
		this.partA = null;
		this.partB = null;
	}


	@Override
	public String getType() {
		return "Connector";
	}

	@Override
	public List<String> getSpecs() {
		List<String> specs = super.getSpecs();
		if (partA == null || partB == null) return specs;

		String spec = "[Connector: <"  + partA.getType() + ", " +
				                         partB.getType() + ">]";
		specs.add(spec);
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "{ Connector<PartA, PartB> } \n";
		return str;
	}

}
