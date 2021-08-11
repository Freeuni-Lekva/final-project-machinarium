package com.machinarium.model.Item;

public class Item {
	private final String name;
	private final String nameID;

	public Item(String name, String nameID) {
		this.name = name;
		this.nameID = nameID;
	}


	public String getName() {
		return name;
	}

	public String getNameID() {
		return nameID;
	}

	@Override
	public String toString() {
		String str = "";
		str += "[name: " + name + "] \n";
		str += "[nameID: " + nameID + "] \n";
		return str;
	}

}
