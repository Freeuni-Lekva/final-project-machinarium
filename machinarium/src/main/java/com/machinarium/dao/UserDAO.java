package com.machinarium.dao;

import com.machinarium.model.user.EncryptedPassword;
import com.machinarium.model.user.User;

public interface UserDAO {

	/**
	 * @param userName The user name of the user.
	 * @return The user with the specified user name as a {@link User} or null if one doesn't exist.
	 */
	User getUser(String userName);

	/**
	 * @param userName The user name of the user.
	 * @return The win count for the specified user.
	 */
	int getWinCount(String userName);

	/**
	 * @param userName The user name of the user.
	 * @return The loss count for the specified user.
	 */
	int getLossCount(String userName);

	/**
	 * Adds the specified user to the database.
	 *
	 * @param newUser The new user as a {@link User} object.
	 * @return True if the user was successfully added, false if one with the same username already exists.
	 */
	boolean addUser(User newUser);

	/**
	 * Updates the password for the specified user.
	 *
	 * @param userName The user name of the user to be updated.
	 * @param newPassword The new password as an {@link EncryptedPassword}.
	 * @return True if the entry for the specified user was successfully updated, false otherwise.
	 */
	boolean updatePassword(String userName, EncryptedPassword newPassword);

	/**
	 * Updates the email for the specified user.
	 *
	 * @param userName The user name of the user to be updated.
	 * @param newEmail The new email.
	 * @return True if the entry for the specified user was successfully updated, false otherwise.
	 */
	boolean updateEmail(String userName, String newEmail);

	/**
	 * Increments the win count of the specified user.
	 *
	 * @param userName The user name of the user to be updated.
	 * @return True if the entry for the specified user was successfully updated, false otherwise.
	 */
	boolean addWin(String userName);

	/**
	 * Increments the loss count of the specififed user.
	 *
	 * @param userName The user name of the user to be updated.
	 * @return True if the entry for the specified user was successfully updated, false otherwise.
	 */
	boolean addLoss(String userName);
}