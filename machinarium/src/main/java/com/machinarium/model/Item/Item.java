package com.machinarium.model.Item;

public class Item {
	public static final int NONE_UID = -1;

	private final int uid;
	private final String name;

	public Item(int uid, String name) {
		this.uid = uid;
		this.name = name;
	}


	public int getUid() {
		return uid;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		String str = "";
		str += "[uid: " + uid + "] \n";
		str += "[name: " + name + "] \n";
		return str;
	}

}
