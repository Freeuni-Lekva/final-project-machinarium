package model;

public class Item {
	public static final int NA_SPEC = -1;
	private static final String DEFAULT_NAME = "Some Spare Item";

	private String name;
	private Category category;

	private PartType partTypeThis;
	private PartModel partModelThis; //ToDo :: refactoring //recently added
	private int weight;
	private int weightSupport;
	private int aeroDrag;
	private int horsePower;
	private int tractionUnit;

	private PartType partTypeA;
	private PartType partTypeB;


	public Item() {
		name = DEFAULT_NAME ;
		category = Category.NONE;

		partTypeThis = PartType.NONE;
		weight = NA_SPEC;
		weightSupport = NA_SPEC;
		aeroDrag = NA_SPEC;
		horsePower = NA_SPEC;
		tractionUnit = NA_SPEC;

		partTypeA = PartType.NONE;
		partTypeB = PartType.NONE;
	}

	public Item(String name, Category category, PartType partTypeThis,
				int weight, int weightSupport, int aeroDrag,
				int horsePower, int tractionUnit) {
		this.name = name;
		this.category = category;

		this.partTypeThis = partTypeThis;
		this.weight = weight;
		this.weightSupport = weightSupport;
		this.aeroDrag = aeroDrag;
		this.horsePower = horsePower;
		this.tractionUnit = tractionUnit;

		partTypeA = PartType.NONE;
		partTypeB = PartType.NONE;
	}

	public Item(String name, Category category, PartType partTypeA, PartType partTypeB) {
		this.name = name;
		this.category = category;

		partTypeThis = PartType.NONE;
		weight = NA_SPEC;
		weightSupport = NA_SPEC;
		aeroDrag = NA_SPEC;
		horsePower = NA_SPEC;
		tractionUnit = NA_SPEC;

		this.partTypeA = partTypeA;
		this.partTypeB = partTypeB;
	}


	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Category getCategory() {return category; }
	public void setCategory(Category category) { this.category = category; }


	public PartType getPartTypeThis() { return partTypeThis; }
	public void setPartTypeThis(PartType partTypeThis) { this.partTypeThis = partTypeThis; }

	public int getWeight() { return weight; }
	public void setWeight(int weight) { this.weight = weight; }

	public int getWeightSupport() { return weightSupport; }
	public void setWeightSupport(int weightSupport) { this.weightSupport = weightSupport; }

	public int getAeroDrag() { return aeroDrag; }
	public void setAeroDrag(int aeroDrag) { this.aeroDrag = aeroDrag; }

	public int getHorsePower() { return horsePower; }
	public void setHorsePower(int horsePower) { this.horsePower = horsePower; }

	public int getTractionUnit() { return tractionUnit; }
	public void setTractionUnit(int tractionUnit) { this.tractionUnit = tractionUnit; }


	public PartType getPartTypeA() { return partTypeA; }
	public void setPartTypeA(PartType partTypeA) { this.partTypeA = partTypeA; }

	public PartType getPartTypeB() { return partTypeB; }
	public void setPartTypeB(PartType partTypeB) { this.partTypeB = partTypeB; }

}
