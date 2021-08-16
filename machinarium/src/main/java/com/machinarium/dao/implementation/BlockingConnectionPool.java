package com.machinarium.dao.implementation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.machinarium.dao.ConnectionPool;
import org.apache.commons.dbcp2.BasicDataSource;

public class BlockingConnectionPool implements ConnectionPool {

    public static final int MAX_N_CONNECTIONS = 100;
    public static final int DEFAULT_N_CONNECTIONS = 10;

    /**
     * Returns an instance of a connection pool. If an open connection pool doesn't
     * exist a new one is created with the <b>default number</b> of connections.
     *
     * @return The connection pool as a {@link ConnectionPool}.
     */
    public static ConnectionPool getInstance() {
        return BlockingConnectionPool.getInstance(DEFAULT_N_CONNECTIONS);
    }

    /**
     * Returns an instance of a connection pool. If an open connection pool doesn't
     * exist a new one is created with the <b>specified number</b> of connections.
     *
     * @param nConnections Number of required connections.
     * @return The connection pool as a {@link ConnectionPool}.
     */
    public static ConnectionPool getInstance(int nConnections) {

        if(connectionPool == null) connectionPool = new BlockingConnectionPool(nConnections);
        return connectionPool;
    }

    /**
     * Returns a connection from the specified pool if one is available, otherwise the {@link Thread} is
     * blocked until another releases a connection.
     *
     * @return The DB connection as a {@link Connection} object.
     */
    @Override
    public Connection acquireConnection() {

        try {
            return this.connections.take();
        } catch (InterruptedException e) {e.printStackTrace();}

        return null;
    }

    /**
     * Releases a connection back to the connection pool.
     *
     * @param connection The {@link Connection} to be released.
     *
     * @throws RuntimeException If the connection has already been released.
     */
    @Override
    public void releaseConnection(Connection connection) {

        if(this.connections.contains(connection)) {
            throw new RuntimeException("You cannot release the same connection twice.");
        }

        this.connections.add(connection);
    }

    /**
     * Waits for all connections to be returned to the pool and closes them.
     *
     * @throws Exception If a connection cannot successfully be closed.
     */
    @Override
    public void close() throws Exception {

        for(int i = 0; i < this.nConnections; i++) connections.take().close();

        connectionPool = null;
    }

    private BlockingConnectionPool(int nConnections) {

        if(nConnections > MAX_N_CONNECTIONS) {
            throw new IllegalArgumentException("You cannot create a connection pool with more than " +
                    MAX_N_CONNECTIONS + " connections.");
        }

        this.nConnections = nConnections;
        this.connections = new ArrayBlockingQueue<>(nConnections);

        try {
            for(int i = 0; i < nConnections; i++) this.connections.add(dataSource.getConnection());
        } catch (SQLException e) {e.printStackTrace();}
    }

    private final int nConnections;
    private final BlockingQueue<Connection> connections;

    private static final BasicDataSource dataSource;
    private static BlockingConnectionPool connectionPool;

    /**
     * Data Source configuration.
     */
    static {

        // TODO: Change this to use your own data base for testing.
        final String USER_NAME = "root";
        final String PASSWORD = "root"; // Data_base1
        final String HOST = "localhost";
        final String PORT = "3306";
        final String DATA_BASE = "machinarium_database";

        dataSource = new BasicDataSource();

        dataSource.setMaxTotal(MAX_N_CONNECTIONS);
        dataSource.setUsername(USER_NAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setUrl("jdbc:mysql://"+ HOST + ":"+ PORT + "/" + DATA_BASE);
    }
}
