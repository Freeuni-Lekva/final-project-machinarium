package com.machinarium.model.car;

import com.machinarium.model.Item.Item;
import com.machinarium.model.Item.connector.Connector;
import com.machinarium.model.Item.part.*;
import com.machinarium.utility.common.ID;
import com.machinarium.utility.constants.CarConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class DragCar extends Car {
	private static final int TIME_ADJUSTER = 100;
	private static final double WEATHER_EFFECT_LO = 0.9;
	private static final double WEATHER_EFFECT_HI = 1.1;
	private static final double TARMAC_EFFECT_LO = 0.9;
	private static final double TARMAC_EFFECT_HI = 1.1;

	private final Chassis chassis;
	private final Body body;
	private final Engine engine;
	private final Transmission transmission;
	private final Wheels wheels;

	private final Connector<Chassis, Body> chassisBody;
	private final Connector<Chassis, Transmission> chassisTransmission;
	private final Connector<Chassis, Wheels> chassisWheels;
	private final Connector<Chassis, Engine> chassisEngine;
	private final Connector<Engine, Transmission> engineTransmission;
	private final Connector<Transmission, Wheels> transmissionWheels;

	private final List<Item> components;

	public DragCar(ID iD, String name,

				   Chassis chassis,
				   Body body,
				   Engine engine,
				   Transmission transmission,
				   Wheels wheels,

				   Connector<Chassis, Body> chassisBody,
				   Connector<Chassis, Transmission> chassisTransmission,
				   Connector<Chassis, Wheels> chassisWheels,
				   Connector<Chassis, Engine> chassisEngine,
				   Connector<Engine, Transmission> engineTransmission,
				   Connector<Transmission, Wheels> transmissionWheels) {

		super(iD, name);

		this.chassis = chassis;
		this.body = body;
		this.engine = engine;
		this.transmission = transmission;
		this.wheels = wheels;

		this.chassisBody = chassisBody;
		this.chassisTransmission = chassisTransmission;
		this.chassisWheels = chassisWheels;
		this.chassisEngine = chassisEngine;
		this.engineTransmission = engineTransmission;
		this.transmissionWheels = transmissionWheels;

		this.components = new ArrayList<>() {{
			add(chassis); add(body); add(engine); add(transmission); add(wheels);
			add(chassisBody); add(chassisTransmission); add(chassisWheels);
			add(chassisEngine); add(engineTransmission); add(transmissionWheels);
		}};
	}

	@Override
	public String getType() {
		return CarConstants.DRAG_CAR_TYPE;
	}

	@Override
	public List<Item> getComponents() {return this.components;}

	@Override
	public boolean isValid() {

		if (!allItemsArePresent()) return false;

		int weightSupport = chassis.getWeightSupport();
		weightSupport -= calcFullWeight();

		return weightSupport >= 0;
	}

	@Override
	public double quarterMileTime() {

		if (!isValid()) return TIME_NA;
		double runTime = 0;

		int engineHorsePower = engine.getHorsePower();
		int tractionLimitHorsePower = wheels.getTractionUnit() * TU_TO_HP;
		int wheelHorsePower = Math.min(engineHorsePower, tractionLimitHorsePower);
		int aeroDrag = body.getAeroDrag();
		int effectiveHorsePower = wheelHorsePower - aeroDrag * AD_TO_HP;
		int fullWeight = calcFullWeight();

		runTime = measureRunTime(effectiveHorsePower, fullWeight);
		return runTime;
	}

	private boolean allItemsArePresent() {

		if (getID() == null) return false;
		if (getName() == null) return false;

		return !components.contains(null);
	}

	private int calcFullWeight() {

		return Stream.of(chassis, body, engine, transmission, wheels).mapToInt(Part::getWeight).sum();
	}

	private double measureRunTime(int effectiveHorsePower, int fullWeight) {

		double time = (double)effectiveHorsePower / (double)fullWeight;
		time *= applyWeatherEffect();
		time *= applyTarmacEffect();
		time = adjustTimer(time);

		return time;
	}

	private double applyWeatherEffect() {

		Random weatherEffectAnalyser = new Random();

		return WEATHER_EFFECT_LO + (WEATHER_EFFECT_HI - WEATHER_EFFECT_LO) * weatherEffectAnalyser.nextDouble();
	}

	private double applyTarmacEffect() {

		Random tarmacEffectAnalyser = new Random();

		return TARMAC_EFFECT_LO + (TARMAC_EFFECT_HI - TARMAC_EFFECT_LO) * tarmacEffectAnalyser.nextDouble();
	}

	private double adjustTimer(double time) {
		return time * TIME_ADJUSTER;
	}
}
