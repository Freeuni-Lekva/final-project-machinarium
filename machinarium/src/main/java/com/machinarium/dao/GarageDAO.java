package com.machinarium.dao;

import com.machinarium.model.Item.Item;
import com.machinarium.model.car.Car;
import com.machinarium.utility.common.ID;
import java.util.List;
import java.util.Map;

public interface GarageDAO {

	/** ========================== Car Contents ========================== **/


	/**
	 * @param userName The username of the user.
	 * @return True if the specified user has a car in his garage, false otherwise.
	 */
	boolean hasCar(String userName);

	/**
	 * @param userName The username of the user.
	 * @return Returns the number of cars the specified user has.
	 */
	int getCarCount(String userName);

	/**
	 * Updates the name of the car with the specified ID.
	 *
	 * @param carID The {@link ID} of the car.
	 * @param newCarName The new name for the car.
	 * @return True if the car was successfully renamed.
	 */
	boolean updateCarName(ID carID, String newCarName);

	/**
	 * Adds a new empty car to the garage of the specified user.
	 *
	 * @param userName The username of the user.
	 * @param carName The name of the car.
	 * @return The ID of the newly added car as an {@link ID}, or null if a car couldn't be added.
	 */
	ID addEmptyCar(String userName, String carName);

	/**
	 * Removes the car with the specified ID.
	 * @param carID The {@link ID} of the car.
	 * @return True if the car was successfully removed.
	 */
	boolean removeCar(ID carID);

	/**
	 * Returns the car with the speceified ID.
	 *
	 * @param carID The {@link ID} of the car.
	 * @return The car as a {@link Car} object, or null if a car with the specified ID doesn't exist.
	 */
	Car getCar(ID carID);

	/**
	 * Returns a list of all the components the car with the specified ID contains.
	 *
	 * @param carID The {@link ID} of the car.
	 * @return A list of items for the specified car.
	 */
	List<Item> getAllCarItems(ID carID);

	/**
	 * Returns a list of all the cars for the specified user.
	 *
	 * @param userName The username of the user
	 * @return The list of cars as a {@Link List<Car>} object.
	 */
	List<Car> getAllCars(String userName);

	/**
	 * @param carID The {@link ID} of the car.
	 * @param itemID The {@link ID} of the item.
	 * @return True if the specified car contains the item, false otherwise.
	 */
	boolean carHasItem(ID carID, ID itemID);

	/**
	 * Adds an item to the car with the specified ID.
	 *
	 * @param carID The {@link ID} of the car.
	 * @param itemID The {@link ID} of the item.
	 * @return True if the item was successfully added, false otherwise.
	 */
	boolean addItemToCar(ID carID, ID itemID);

	/**
	 * Removes an item from the car with the specified ID.
	 *
	 * @param carID The {@link ID} of the car.
	 * @param itemID The {@link ID} of the item.
	 * @return True if the item was successfully removed, false otherwise.
	 */
	boolean removeItemFromCar(ID carID, ID itemID);


	/** ========================== Item Contents ========================== **/


	/**
	 * @param userName The username of the user.
	 * @return True if the specified user has a spare item in their garage.
	 */
	boolean hasSpareItem(String userName);

	/**
	 * @param userName The username of the user.
	 * @return The number of spare items in the garage of the specified user.
	 */
	int getSpareItemCount(String userName);

	/**
	 * @param userName The username of the user.
	 * @param itemID The {@link ID} of the item.
	 * @return True if the specified user has
	 */
	boolean hasThisSpareItem(String userName, ID itemID);

	/**
	 * @param userName The username of the user.
	 * @param itemID The {@link ID} of the item.
	 * @return The number of the specified item the user has in their garage.
	 */
	int getThisSpareItemCount(String userName, ID itemID);

	/**
	 * Adds a number of the specified item to the users garage.
	 *
	 * @param userName The username of the user.
	 * @param itemID The {@link ID} of the item.
	 * @param count The number of items to add.
	 * @return True if the items were successfully added, false otherwise.
	 */
	boolean addSpareItem(String userName, ID itemID, int count);

	/**
	 * Removes a number of the specified item from the users garage.
	 *
	 * @param userName The username of the user.
	 * @param itemID The {@link ID} of the item.
	 * @param count The number of items to remove.
	 * @return True if the items were successfully removed, false otherwise.
	 */
	boolean removeSpareItem(String userName, ID itemID, int count);

	/**
	 * Returns a mapping of all the spare items in the specified users garage to their
	 * respective amounts.
	 *
	 * @param userName The username of the user.
	 * @return The spare items as a {@link Map<Item, Integer>} object.
	 */
	Map<Item, Integer> getAllSpareItems(String userName);

}
