package com.machinarium.model_v1;

import java.util.ArrayList;
import java.util.List;

public class Garage {
	private String postCode; //ToDo :: refactoring //recently added
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
