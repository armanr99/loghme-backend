package com.loghme.configs;

public class DatabaseConfigs {
    public static String driverClassName = "com.mysql.jdbc.Driver";
    public static String url = "jdbc:mysql://localhost:3306/loghme";
    public static String username = "root";
    public static String password = "g;QK\"7MP";
    public static int minIdle = 5;
    public static int maxIdle = 100;
    public static int maxOpenPreparedStatements = 500;
}
