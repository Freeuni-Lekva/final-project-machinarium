package com.machinarium.model.Item.connector;

import com.machinarium.model.Item.Item;

public abstract class CarConnector implements Connector, Item {
	String name;
	String nameID;

	public CarConnector(String name, String nameID) {
		this.name = name;
		this.nameID = nameID;
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
		str += "[nameID: " + nameID + "]";
		return str;
	}

}
