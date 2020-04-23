package com.loghme.database.ConncetionPool;

import com.loghme.configs.DatabaseConfigs;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private BasicDataSource dataSource;
    private static ConnectionPool instance;

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    private ConnectionPool() {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DatabaseConfigs.driverClassName);
        dataSource.setUrl(DatabaseConfigs.url);
        dataSource.setUsername(DatabaseConfigs.username);
        dataSource.setPassword(DatabaseConfigs.password);
        dataSource.setMinIdle(DatabaseConfigs.minIdle);
        dataSource.setMaxIdle(DatabaseConfigs.maxIdle);
        dataSource.setMaxOpenPreparedStatements(DatabaseConfigs.maxOpenPreparedStatements);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
