package com.machinarium.model.Item.part;

import com.machinarium.model.Item.Item;
import java.util.List;
import java.util.Map;

public abstract class Part extends Item {
	public static final int NONE_WEIGHT = -1;

	private final int weight;


	public Part(int ID, String name, int weight) {
		super(ID, name);
		this.weight = weight;
	}

	public Part() {
		super();
		this.weight = NONE_WEIGHT;
	}


	public int getWeight() {
		return weight;
	}

	@Override
	public String getType() {
		return "Part";
	}

	@Override
	public List<String> getSpecs() {
		List<String> specs = super.getSpecs();
		String spec = "[weight: " + weight + "]";
		specs.add(spec);
		return specs;
	}

	@Override
	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = super.getMappedSpecs();
		specs.put("weight", weight + "");
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[weight: " + weight + "] \n";
		return str;
	}

}
