package com.machinarium.dao;

public interface UserStatisticsDAO {

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
