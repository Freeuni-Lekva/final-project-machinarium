package model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class Car {
	private static final int INVALID_WEIGHT_SUPPORT = -1;
	private static final int INVALID_TIME = -1;
	private static final String DEFAULT_NAME = "Project Car";

	private String name;
	private List<Item> items;


	public Car() {
		name = DEFAULT_NAME;
		items = new ArrayList<>();
	}

	public Car(String name) {
		this.name = name;
		items = new ArrayList<>();
	}

	public Car(String name, List<Item> items) {
		this.name = name;
		this.items = items;
	}



	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public List<Item> getItems() { return items; }
	public List<Item> takeApart() {
		List<Item> partsContainer = new ArrayList<>(items);
		items.clear();
		return partsContainer;
	}
	public void setItems(List<Item> items) { this.items = items; }
	public void addItem(Item item) { items.add(item); }

	public boolean isEmptyCar() { return items.isEmpty(); }





	public boolean isValid() {
		Set<Category> allCategory = EnumSet.allOf(Category.class);
		allCategory.remove(Category.NONE);

		Set<PartType> allPartType = EnumSet.allOf(PartType.class);
		allPartType.remove(PartType.NONE);

		int weightSupport = INVALID_WEIGHT_SUPPORT;


		for (Item curItem : items) {
			Category curCategory = curItem.getCategory();
			if (curCategory == Category.NONE) { return false; }
			if (allCategory.contains(curCategory)) { allCategory.remove(curCategory); }


			if (curCategory == Category.PART) {
				if (curItem.getPartTypeThis() == PartType.CHASSIS) { weightSupport = curItem.getWeight(); }
				weightSupport -= curItem.getWeight();

			} else if (curCategory == Category.CONNECTOR || curCategory == Category.FUSION_TOOL) {


			} else {
				return false;
			}
		}





		return false; //ToDo
	}

	public int dragTime() {
		return INVALID_TIME; //ToDo
	}

}
