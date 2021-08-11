package com.machinarium.model.Item.part;

public class Engine extends Part {
	private final int horsePower;

	public Engine(String name, String nameID, int weight, int horsePower) {
		super(name, nameID, weight);
		this.horsePower = horsePower;
	}


	public int getHorsePower() {
		return horsePower;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[horsePower: " + horsePower + "] \n";
		return str;
	}

}
