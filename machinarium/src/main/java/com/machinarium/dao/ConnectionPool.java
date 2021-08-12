package com.machinarium.dao;

import java.sql.Connection;

public interface ConnectionPool extends AutoCloseable {

    /**
     * Returns a connection from the specified pool if one is available, otherwise the {@link Thread} is
     * blocked until another releases a connection.
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
