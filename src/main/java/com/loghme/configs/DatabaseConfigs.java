package com.loghme.configs;

public class DatabaseConfigs {
    public static String driverClassName = "com.mysql.cj.jdbc.Driver";
    public static String url = "jdbc:mysql://localhost:3306/loghme?useUnicode=yes&characterEncoding=UTF-8";
    public static String username = "root";
    public static String password = "g;QK\"7MP";
    public static int minIdle = 50;
    public static int maxIdle = 250;
    public static int maxOpenPreparedStatements = 500;
}
