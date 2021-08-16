package com.machinarium.dao;

import com.machinarium.model.car.Car;
import com.machinarium.utility.common.ID;

import java.util.List;

public interface GameDAO {

	enum GameStage {
		IN_LOBBY(1),
		ACTIVE(2),
		FINISHED(3);

		public static String getActiveStages() {return "('" + ACTIVE + "', '" + IN_LOBBY + "')";}

		public static GameStage of(String stageName) {return valueOf(stageName.toUpperCase());}

		private GameStage(int stage) {this.stage = stage;}

		private final int stage;
	};

	/**
	 * Returns the host user of the specified game.
	 *
	 * @param gameID The id of the game as an {@link ID} object.
	 * @return The username of the host for the specified game, or null if one doesn't exist.
	 */
	String getGameHost(ID gameID);

	/**
	 * Returns a list of all users participating in the game with the specified id.
	 *
	 * @param gameID The id of the game as an {@link ID} object.
	 * @return A {@link List} of the usernames of all the game participants.
	 */
	List<String> getGameUsers(ID gameID);

	/**
	 * Returns the active game of the specified user.
	 *
	 * @param userName The username of the user.
	 * @return The id of the active game as an {@Link ID} object, or null if there is none.
	 */
	ID getActiveGame(String userName);

	/**
	 * Returns a list of all active games.
	 *
	 * @return A {@link List} of the {@link ID}s of all currently active games.
	 */
	List<ID> getAllActiveGames();

	/**
	 * Returns the current stage of the specified game.
	 *
	 * @param gameID The id of the game as an {@link ID} object.
	 * @return The stage of the specified game as a {@link GameStage}.
	 */
	GameStage getGameStage(ID gameID);

	/**
	 *
	 * @param userName
	 * @param gameID
	 * @return
	 */
	Car getUserChosenCar(String userName, ID gameID);

	/**
	 * Creates a new game lobby with the specified host.
	 *
	 * @param hostName Username of the host user.
	 * @return
	 */
	ID addGame(String hostName);

	/**
	 * Adds the user to the game with the specified id.
	 *
	 * @param gameID The id of the game as an {@link ID} object.
	 * @param userName The username of the user.
	 * @return True if the user was successfully added.
	 */
	boolean addUser(ID gameID, String userName);

	/**
	 * Updates the stage of the specified game.
	 *
	 * @param gameID The id of the game as a {@link ID} object.
	 * @param newStage The new stage for the game as a {@link GameStage}.
	 * @return True if the game stage was successfully updated.
	 */
	boolean updateGameStage(ID gameID, GameStage newStage);
}
