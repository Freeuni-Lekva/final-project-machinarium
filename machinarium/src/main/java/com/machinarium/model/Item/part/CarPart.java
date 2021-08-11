package com.machinarium.model.Item.part;

import com.machinarium.model.Item.Item;

public abstract class CarPart extends Item {
	private int weight;

	public CarPart(String name, String nameID, int weight) {
		super(name, nameID);
		this.weight = weight;
	}


	public int getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[weight: " + weight + "] \n";
		return str;
	}

}
