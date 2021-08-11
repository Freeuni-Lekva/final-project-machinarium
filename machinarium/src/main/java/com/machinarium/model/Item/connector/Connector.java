package com.machinarium.model.Item.connector;

import com.machinarium.model.Item.Item;

public class Connector<PartA, PartB> extends Item {

	public Connector(int uid, String name) {
		super(uid, name);
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "{ Connector<PartA, PartB> } \n";
		return str;
	}

}
