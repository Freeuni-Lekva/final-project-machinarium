package model;

import java.util.ArrayList;
import java.util.List;

public class Garage {
	private List<Item> spareItems;
	private List<Car> cars;


	public Garage() {
		spareItems = new ArrayList<>();
		cars = new ArrayList<>();
	}

	public Garage(List<Item> spareItems) {
		this.spareItems = spareItems;
		cars = new ArrayList<>();
	}

	public Garage(List<Item> spareItems, List<Car> cars) {
		this.spareItems = spareItems;
		this.cars = cars;
	}


}
