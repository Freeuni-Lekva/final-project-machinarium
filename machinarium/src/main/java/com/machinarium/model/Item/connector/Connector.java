package com.machinarium.model.Item.connector;

import com.machinarium.model.Item.Item;
import com.machinarium.model.Item.part.Part;
import java.util.List;
import java.util.Map;

public class Connector<PartA, PartB> extends Item {
	private final Part partA;
	private final Part partB;


	public Connector(int ID, String name, Part partA, Part partB) {
		super(ID, name);
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
		String spec = nextSpecValidated();
		specs.add(spec);
		return specs;
	}

	@Override
	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = super.getMappedSpecs();
		String spec = nextSpecValidated();
		specs.put("Connector", spec);
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += nextSpecValidated() + " \n";
		return str;
	}


	private String nextSpecValidated() {
		String nextSpec = "";

		if (partA == null || partB == null) {
			nextSpec = "[Connector: <PartA, PartB>]";

		} else {
			nextSpec = "[Connector: <" + partA.getType() + ", " +
										 partB.getType() + ">]";
		}

		return nextSpec;
	}

}
