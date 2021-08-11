package com.machinarium.model.Item.part;

public class Engine extends Part {
	private final int horsePower;

	public Engine(int uid, String name, int weight, int horsePower) {
		super(uid, name, weight);
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
