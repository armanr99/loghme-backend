package com.loghme.database.ConncetionPool;

import com.loghme.configs.DatabaseConfigs;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setDriverClassName(DatabaseConfigs.driverClassName);
        ds.setUrl(DatabaseConfigs.url);
        ds.setUsername(DatabaseConfigs.username);
        ds.setPassword(DatabaseConfigs.password);
        ds.setMinIdle(DatabaseConfigs.minIdle);
        ds.setMaxIdle(DatabaseConfigs.maxIdle);
        ds.setMaxOpenPreparedStatements(DatabaseConfigs.maxOpenPreparedStatements);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private ConnectionPool() {
    }
}
