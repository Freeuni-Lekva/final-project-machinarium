package com.machinarium.model.car;

import com.machinarium.model.Item.connector.*;
import com.machinarium.model.Item.part.*;

public class DragCar implements Car {
	private final String name;
	private final String nameID;

	private final Chassis chassis;
	private final Body body;
	private final Engine engine;
	private final Transmission transmission;
	private final Wheels wheels;

	private final Chassis_Body chassis_body;
	private final Chassis_Transmission chassis_transmission;
	private final Chassis_Wheels chassis_wheels;
	private final Chassis_Engine chassis_engine;
	private final Engine_Transmission engine_transmission;
	private final Transmission_Wheels transmission_wheels;

	public DragCar(String name, String nameID,
				   Chassis chassis, Body body, Engine engine,
				   Transmission transmission, Wheels wheels,
				   Chassis_Body chassis_body, Chassis_Transmission chassis_transmission,
				   Chassis_Wheels chassis_wheels, Chassis_Engine chassis_engine,
				   Engine_Transmission engine_transmission,
				   Transmission_Wheels transmission_wheels) {

		this.name = name;
		this.nameID = nameID;

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
	public String getName() {
		return name;
	}

	@Override
	public String getNameID() {
		return nameID;
	}

	@Override
	public boolean isValid() {  //ToDo: implement
		return false;
	}

}
