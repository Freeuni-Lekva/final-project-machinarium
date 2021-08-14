package com.machinarium.model.Item.part;

import com.machinarium.utility.common.ID;
import com.machinarium.utility.constants.ItemConstants;

public class Transmission extends Part {

	public Transmission(ID iD, String name, Integer weight) {
		super(iD, name, weight);
	}


	@Override
	public String getType() {
		return ItemConstants.TRANSMISSION_TYPE;
	}

}
