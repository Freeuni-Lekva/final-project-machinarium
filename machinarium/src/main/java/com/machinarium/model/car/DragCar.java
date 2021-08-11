package com.machinarium.model.car;

import com.machinarium.model.Item.connector.*;
import com.machinarium.model.Item.part.*;
import java.util.Random;

public class DragCar extends CarAbs {
	private Chassis chassis;
	private Body body;
	private Engine engine;
	private Transmission transmission;
	private Wheels wheels;

	////////////////////////////////////// private Conn<Chassis, Body> chassisBody;


	private Chassis_Body chassis_body;
	private Chassis_Transmission chassis_transmission;
	private Chassis_Wheels chassis_wheels;
	private Chassis_Engine chassis_engine;
	private Engine_Transmission engine_transmission;
	private Transmission_Wheels transmission_wheels;



	public DragCar(String name, String nameID,
				   Chassis chassis, Body body, Engine engine,
				   Transmission transmission, Wheels wheels,
				   Chassis_Body chassis_body, Chassis_Transmission chassis_transmission,
				   Chassis_Wheels chassis_wheels, Chassis_Engine chassis_engine,
				   Engine_Transmission engine_transmission,
				   Transmission_Wheels transmission_wheels) {

		super(name, nameID);

		this.chassis = chassis;
		this.body = body;
		this.engine = engine;
		this.transmission = transmission;
		this.wheels = wheels;

		this.chassis_body = chassis_body;
		this.chassis_transmission = chassis_transmission;
		this.chassis_wheels = chassis_wheels;
		this.chassis_engine = chassis_engine;
		this.engine_transmission = engine_transmission;
		this.transmission_wheels = transmission_wheels;
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

		if (chassis_body == null) return false;
		if (chassis_transmission == null) return false;
		if (chassis_wheels == null) return false;
		if (chassis_engine == null) return false;
		if (engine_transmission == null) return false;
		if (transmission_wheels == null) return false;

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

}
