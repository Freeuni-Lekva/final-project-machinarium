package com.machinarium.model.Item.part;

public class Transmission extends Part {

	public Transmission(int uid, String name, int weight) {
		super(uid, name, weight);
	}

	public Transmission() {
		super();
	}


	@Override
	public String getType() {
		return "Transmission";
	}

}
