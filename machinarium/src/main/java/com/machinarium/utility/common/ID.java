package com.machinarium.utility.common;

public class ID {
	private final int id;

	public static ID of(int id) {return new ID(id);}

	public int getID() {
		return id;
	}

	@Override
	public String toString() {
		return String.valueOf(this.id);
	}

	private ID(int id) {
		this.id = id;
	}
}
