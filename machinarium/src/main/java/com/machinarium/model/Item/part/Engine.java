package com.machinarium.model.Item.part;

import java.util.List;
import java.util.Map;

public class Engine extends Part {
	public static final int NONE_HORSE_POWER = -1;

	private final int horsePower;


	public Engine(int uid, String name, int weight, int horsePower) {
		super(uid, name, weight);
		this.horsePower = horsePower;
	}

	public Engine() {
		super();
		this.horsePower = NONE_HORSE_POWER;
	}


	public int getHorsePower() {
		return horsePower;
	}

	@Override
	public String getType() {
		return "Engine";
	}

	@Override
	public List<String> getSpecs() {
		List<String> specs = super.getSpecs();
		String spec = "[horsePower: " + horsePower + "]";
		specs.add(spec);
		return specs;
	}

	@Override
	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = super.getMappedSpecs();
		specs.put("horsePower", horsePower + "");
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[horsePower: " + horsePower + "] \n";
		return str;
	}

}
