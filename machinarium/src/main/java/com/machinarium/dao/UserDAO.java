package com.machinarium.dao;


import com.machinarium.utility.EncryptedPassword;
import com.machinarium.model.user.User;

public interface UserDAO {

    /**
     * @param userName The user name of the user.
     * @return The user with the specified user name as a {@link User} object or null if one doesn't exist.
     */
    User getUser(String userName);

    /**
     * Adds the specified user to the database.
     * @param userName The user name of the user.
     * @param encryptedPassword The password as an {@link EncryptedPassword}.
     * @param email The email.
     * @return True if the user was successfully added, false if one with the same username already exists.
     */
    boolean addUser(String userName, EncryptedPassword encryptedPassword, String email);

    /**
     * Updates the password for the specified user.
     *
     * @param userName The user name of the user to be updated.
     * @param newEncryptedPassword The new password as an {@link EncryptedPassword}.
     * @return True if the entry for the specified user was successfully updated, false otherwise.
     */
    boolean updatePassword(String userName, EncryptedPassword newEncryptedPassword);

    /**
     * Updates the email for the specified user.
     *
     *
     * @param userName The user name of the user to be updated.
     * @param newEmail The new email.
     * @return True if the entry for the specified user was successfully updated, false otherwise.
     */
    boolean updateEmail(String userName, String newEmail);

}
