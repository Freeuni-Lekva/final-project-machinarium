package com.machinarium.model.Item.part;

import com.machinarium.model.Item.Item;

public abstract class Part extends Item {
	private final int weight;

	public Part(int uid, String name, int weight) {
		super(uid, name);
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
