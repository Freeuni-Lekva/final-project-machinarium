package com.machinarium.model.Item.part;

public class Transmission extends Part {

	public Transmission(int ID, String name, int weight) {
		super(ID, name, weight);
	}

	public Transmission() {
		super();
	}


	@Override
	public String getType() {
		return "Transmission";
	}

}
