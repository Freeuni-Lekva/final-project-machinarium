package com.machinarium.dao;

import java.sql.Connection;

public interface ConnectionPool extends AutoCloseable {

    /**
     * Returns a connection from the specified pool.
     *
     * @return The DB connection as a {@link Connection} object.
     */
    Connection acquireConnection();

    /**
     * Releases a previously acquired connection back to the pool.
     *
     * @param connection The {@link Connection} to be released.
     */
    void releaseConnection(Connection connection);
}
