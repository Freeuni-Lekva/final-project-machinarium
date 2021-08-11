package com.machinarium.model.car;

import com.machinarium.model.Item.connector.*;
import com.machinarium.model.Item.part.*;
import java.util.Random;

public class DragCar extends Car {
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

	public DragCar(String name, String nameID,

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

		super(name, nameID);

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
		if (getName() == null) return false;
		if (getNameID() == null) return false;

		if (chassis == null) return false;
		if (body == null) return false;
		if (engine == null) return false;
		if (transmission == null) return false;
		if (wheels == null) return false;

		if (chassisBody == null) return false;
		if (chassisTransmission == null) return false;
		if (chassisWheels == null) return false;
		if (chassisEngine == null) return false;
		if (engineTransmission == null) return false;
		if (transmissionWheels == null) return false;

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
		double lo = 0.9;
		double hi = 1.1;
		Random weatherEffectAnalyser = new Random();
		double weatherEffect = lo + (hi - lo) * weatherEffectAnalyser.nextDouble();
		return weatherEffect;
	}

	private double applyTarmacEffect() {
		double lo = 0.9;
		double hi = 1.1;
		Random tarmacEffectAnalyser = new Random();
		double tarmacEffect = lo + (hi - lo) * tarmacEffectAnalyser.nextDouble();
		return tarmacEffect;
	}

	private double adjustTimer(double time) {
		return time * 100;
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
