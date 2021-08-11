package com.machinarium.model.Item.part;

import java.util.List;
import java.util.Map;

public class Body extends Part {
	public static final int NONE_AERO_DRAG = -1;

	private final int aeroDrag;


	public Body(int uid, String name, int weight, int aeroDrag) {
		super(uid, name, weight);
		this.aeroDrag = aeroDrag;
	}

	public Body() {
		super();
		this.aeroDrag = NONE_AERO_DRAG;
	}


	public int getAeroDrag() {
		return aeroDrag;
	}

	@Override
	public String getType() {
		return "Body";
	}

	@Override
	public List<String> getSpecs() {
		List<String> specs = super.getSpecs();
		String spec = "[aeroDrag: " + aeroDrag + "]";
		specs.add(spec);
		return specs;
	}

	@Override
	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = super.getMappedSpecs();
		specs.put("aeroDrag", aeroDrag + "");
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[aeroDrag: " + aeroDrag + "] \n";
		return str;
	}

}
