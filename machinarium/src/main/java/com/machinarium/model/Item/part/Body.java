package com.machinarium.model.Item.part;

import com.machinarium.model.globals.ID;
import com.machinarium.model.globals.ItemLib;

import java.util.List;
import java.util.Map;

public class Body extends Part {
	private final int aeroDrag;


	public Body(ID iD, String name, int weight, int aeroDrag) {
		super(iD, name, weight);
		this.aeroDrag = aeroDrag;
	}

	public Body() { //todo remove
		super();
		this.aeroDrag = -1;
	}


	public int getAeroDrag() {
		return aeroDrag;
	}

	@Override
	public String getType() {
		return ItemLib.BODY_TYPE;
	}

	@Override
	public List<String> getSpecs() {
		List<String> specs = super.getSpecs();
		String spec = "[" + ItemLib.AERO_DRAG + ": " + aeroDrag + "]";
		specs.add(spec);
		return specs;
	}

	@Override
	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = super.getMappedSpecs();
		specs.put(ItemLib.AERO_DRAG, aeroDrag + "");
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[" + ItemLib.AERO_DRAG + ": " + aeroDrag + "] \n";
		return str;
	}

}
