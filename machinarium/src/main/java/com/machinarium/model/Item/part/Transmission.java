package com.machinarium.model.Item.part;

import com.machinarium.model.globals.ID;
import com.machinarium.model.globals.ItemLib;

public class Transmission extends Part {

	public Transmission(ID iD, String name, Integer weight) {
		super(iD, name, weight);
	}


	@Override
	public String getType() {
		return ItemLib.TRANSMISSION_TYPE;
	}

}