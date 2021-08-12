package com.machinarium.model.car;

import com.machinarium.model.Item.Item;
import com.machinarium.model.Item.connector.*;
import com.machinarium.model.Item.part.*;

import java.util.*;

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


	public DragCar(int ID, String name,

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

		super(ID, name);

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
	}


	@Override
	public boolean isValid() {
		if (! allItemsArePresent()) return false;

		int weightSupport = chassis.getWeightSupport();
		weightSupport -= calcFullWeight();
		if (weightSupport < 0) return false;

		return true;
	}

	@Override
	public double quarterMileTime() {
		if (! isValid()) return TIME_NA;
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
		if (getID() == NONE_ID) return false;
		if (getName() == null) return false;

		List<Item> necessaryComponents = List.of(
				chassis, body, engine, transmission, wheels,
				chassisBody, chassisTransmission, chassisWheels,
				chassisEngine, engineTransmission, transmissionWheels
		);

		for (Item component: necessaryComponents) {
			if (component == null) return false;
		}

		return true;
	}

	private int calcFullWeight() {
		int fullWeight = 0;
		fullWeight += chassis.getWeightSupport();
		fullWeight += chassis.getWeight();
		fullWeight += body.getWeight();
		fullWeight += engine.getWeight();
		fullWeight += transmission.getWeight();
		fullWeight += wheels.getWeight();
		return fullWeight;
	}

	private double measureRunTime(int effectiveHorsePower, int fullWeight) {
		double time = (double)effectiveHorsePower / (double)fullWeight;
		time *= applyWeatherEffect();
		time *= applyTarmacEffect();
		time = adjustTimer(time);
		return time;
	}

	private double applyWeatherEffect() {
		double lo = WEATHER_EFFECT_LO;
		double hi = WEATHER_EFFECT_HI;
		Random weatherEffectAnalyser = new Random();
		double weatherEffect = lo + (hi - lo) * weatherEffectAnalyser.nextDouble();
		return weatherEffect;
	}

	private double applyTarmacEffect() {
		double lo = TARMAC_EFFECT_LO;
		double hi = TARMAC_EFFECT_HI;
		Random tarmacEffectAnalyser = new Random();
		double tarmacEffect = lo + (hi - lo) * tarmacEffectAnalyser.nextDouble();
		return tarmacEffect;
	}

	private double adjustTimer(double time) {
		return time * TIME_ADJUSTER;
	}


	@Override
	public String getType() {
		return "DragCar";
	}

	@Override
	public List<List<String>> getSpecs() {
		List<List<String>> fullSpecs = new ArrayList<>();

		fullSpecs.add(chassis.getSpecs());
		fullSpecs.add(body.getSpecs());
		fullSpecs.add(engine.getSpecs());
		fullSpecs.add(transmission.getSpecs());
		fullSpecs.add(wheels.getSpecs());

		fullSpecs.add(chassisBody.getSpecs());
		fullSpecs.add(chassisTransmission.getSpecs());
		fullSpecs.add(chassisWheels.getSpecs());
		fullSpecs.add(chassisEngine.getSpecs());
		fullSpecs.add(engineTransmission.getSpecs());
		fullSpecs.add(transmissionWheels.getSpecs());

		return fullSpecs;
	}

	@Override
	public Map<String, Map<String, String>> getMappedSpecs() {
		Map<String, Map<String, String>> fullSpecs = new HashMap<>();

		fullSpecs.put(chassis.getType(), chassis.getMappedSpecs());
		fullSpecs.put(body.getType(), body.getMappedSpecs());
		fullSpecs.put(engine.getType(), engine.getMappedSpecs());
		fullSpecs.put(transmission.getType(), transmission.getMappedSpecs());
		fullSpecs.put(wheels.getType(), wheels.getMappedSpecs());

		fullSpecs.put(chassisBody.getType(), chassisBody.getMappedSpecs());
		fullSpecs.put(chassisTransmission.getType(), chassisTransmission.getMappedSpecs());
		fullSpecs.put(chassisWheels.getType(), chassisWheels.getMappedSpecs());
		fullSpecs.put(chassisEngine.getType(), chassisEngine.getMappedSpecs());
		fullSpecs.put(engineTransmission.getType(), engineTransmission.getMappedSpecs());
		fullSpecs.put(transmissionWheels.getType(), transmissionWheels.getMappedSpecs());

		return fullSpecs;
	}

	@Override
	public String toString() {
		String str = super.toString();

		str += "[chassis: \n" + chassis.toString() + "\n] \n";
		str += "[body: \n" + body.toString() + "\n] \n";
		str += "[engine: \n" + engine.toString() + "\n] \n";
		str += "[transmission: \n" + transmission.toString() + "\n] \n";
		str += "[wheels: \n" + wheels.toString() + "\n] \n";

		str += "[chassisBody: \n" + chassisBody.toString() + "\n] \n";
		str += "[chassisTransmission: \n" + chassisTransmission.toString() + "\n] \n";
		str += "[chassisWheels: \n" + chassisWheels.toString() + "\n] \n";
		str += "[chassisEngine: \n" + chassisEngine.toString() + "\n] \n";
		str += "[engineTransmission: \n" + engineTransmission.toString() + "\n] \n";
		str += "[transmissionWheels: \n" + transmissionWheels.toString() + "\n] \n";

		return str;
	}

}
