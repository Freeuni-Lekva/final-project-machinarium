package com.machinarium.model.Item.part;

import com.machinarium.utility.common.ID;
import com.machinarium.utility.constants.ItemConstants;
import java.util.List;
import java.util.Map;

public class Body extends Part {
	private final Integer aeroDrag;


	public Body(ID iD, String name, Integer weight, Integer aeroDrag) {
		super(iD, name, weight);
		this.aeroDrag = aeroDrag;
	}


	public Integer getAeroDrag() {
		return aeroDrag;
	}

	@Override
	public String getType() {
		return ItemConstants.BODY_TYPE;
	}

	@Override
	public List<String> getSpecs() {
		List<String> specs = super.getSpecs();
		String spec = "[" + ItemConstants.AERO_DRAG + ": " + aeroDrag + "]";
		specs.add(spec);
		return specs;
	}

	@Override
	public Map<String, String> getMappedSpecs() {
		Map<String, String> specs = super.getMappedSpecs();
		specs.put(ItemConstants.AERO_DRAG, aeroDrag.toString());
		return specs;
	}

	@Override
	public String toString() {
		String str = super.toString();
		str += "[" + ItemConstants.AERO_DRAG + ": " + aeroDrag + "] \n";
		return str;
	}

}
