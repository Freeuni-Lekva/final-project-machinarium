package com.machinarium.model.Item.part;

import com.machinarium.model.Item.Item;

public abstract class CarPart implements Part, Item {
	private String name;
	private String nameID;
	private int weight;

	public CarPart(String name, String nameID, int weight) {
		this.name = name;
		this.nameID = nameID;
		this.weight = weight;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getNameID() {
		return nameID;
	}

	@Override
	public String toString() {
		String str = "";
		str += "[name: " + name + "], ";
		str += "[nameID: " + nameID + "], ";
		str += "[weight: " + weight + "]";
		return str;
	}

	public int getWeight() {
		return weight;
	}

}
