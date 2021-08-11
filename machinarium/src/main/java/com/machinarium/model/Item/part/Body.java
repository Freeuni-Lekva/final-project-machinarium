package com.machinarium.model.Item.part;

public class Body extends CarPart {
	private int aeroDrag;

	public Body(String name, String nameID, int weight, int aeroDrag) {
		super(name, nameID, weight);
		this.aeroDrag = aeroDrag;
	}


	public int getAeroDrag() {
		return aeroDrag;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[aeroDrag: " + aeroDrag + "] \n";
		return str;
	}

}
