package com.machinarium.model.Item.part;

import com.machinarium.model.globals.ID;

public class Transmission extends Part {

	public Transmission(ID iD, String name, int weight) {
		super(iD, name, weight);
	}

	public Transmission() {
		super();
	}


	@Override
	public String getType() {
		return "Transmission";
	}

}
